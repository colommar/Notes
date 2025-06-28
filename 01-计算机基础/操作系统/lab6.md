## **实验6 简单模拟文件系统的分析**

### **实验内容**

1. **分析模拟文件系统的实现程序**
2. 对文件的基本管理数据结构和**有关实现过程给出分析，并画出流程图**。
### **实验要求：**

(1) 编译执行模拟文件系统的实现程序，根据系统实现的功能反复操作，直至熟悉所有功能。在磁盘上建立一个文件作为文件存储器，此文件相当于一个磁盘分区，并在其上实现了一个简单的单用户文件系统。

> 文件管理数据结构

- **OpenFileTable**: 用于管理打开的文件，存储文件的偏移量、文件名、文件起始块号和文件长度。
- **FCB_Block (File Control Block)**: 用于存储每个文件的元数据，包括文件名、文件创建日期、文件起始块号、文件长度等信息。`flag` 字段表示该文件是否已被占用。
- **Super_Block**: 存储文件系统的整体信息，如磁盘的总大小、空闲块数量、根目录信息、FAT表起始位置和数据区起始位置等。
```cpp
struct OpenFileTable              //打开文件表数据结构

{

    long   offset;            // 当前文件读写指针

    char file_name[10];       // 文件名数组

    long int file_start;      // 文件起始块号

    long int file_length;     // 文件长度（字节）

};

  

struct FCB_Block              //FCB数据结构

{

    int  flag;            // 标志，-1表示未用，1表示文件用

    char file_name[10];   // 文件名数组

    long int file_date;       // 文件建立日期

    long int file_time;       // 文件建立时间

    long int file_start;      // 文件起始块号

    long int file_length;     // 文件长度（字节）

};

  

struct Super_Block            // 超级块数据结构, 文件系统的分区信息，存放在0#物理块中

{

    unsigned long int fs_totalsize; // 整个分区的总磁盘物理块数

    unsigned long int fs_freesize;  // 分区的所有空闲磁盘物理块数

    unsigned int fs_blocksize;  // 文件系统的物理块大小（字节）

    unsigned int fs_fat_start;  // FAT的起始磁盘物理块号

    unsigned int fs_fat_size;   // FAT占用的磁盘物理块数

    unsigned int fs_dir_start;  // 根目录的起始磁盘物理块号

    unsigned int fs_dir_size;   // 根目录占用的磁盘物理块数

    unsigned int fs_data_start; // 数据区起始磁盘物理块号

    unsigned long int fs_data_size; // 数据区的磁盘物理块数

 };

  

const char DiskName[]="FileSys.dat";  //磁盘文件名

char rw_buffer[512];             // 读写使用的缓冲区

struct FCB_Block filefcb[130];   // 读写目录使用的数据结构

struct Super_Block FsSupBlk;     // 读写超级块使用的数据结构

long int fat_buffer[5000];       // 读写FAT使用的缓冲区，为简化在系统启动时全部装入内存,0为空闲

struct OpenFileTable OFT[16];    // 打开文件表，当前只使用OFT[0]

  
  

unsigned int block_size;           // 物理块大小（字节）

unsigned long int total_disk_size; // 磁盘总容量（物理块数）

unsigned int total_dir_size;       // 目录占有的物理块数

unsigned int total_fat_size;       // FAT占有的物理块数

  

long int find_fcb;  // 记录读FCB块的次数

FILE *fsPtr;        // 模拟磁盘的文件指针
```


(2) 根据代码分析文件系统的磁盘结构、文件目录结构的形式、文件FCB各项的含义以及空闲空间管理的方法对该文件系统的功能实现**给出分析描述**，**画出流程图**，**要求分析的功能：**

- format：对文件系统进行格式化。format
- dir：显示文件。dir
- read：读文件。rf filename start size
- write：写文件。wf filename size **以下功能选作：**
- create：建立文件。cf filename size
- del： 删除文件。df filename

> 文件系统的基本功能分析

- **格式化（format）**: 格式化功能会初始化磁盘结构，包括分配磁盘空间、创建FAT表、初始化目录等。`Real_Format`函数实现了这一过程，具体操作如下：
    
    - 设置磁盘总大小、块大小、目录区和数据区的位置。
    - 清空目录和FAT表，标记空闲的磁盘块。
    
    格式化过程是文件系统的初始化步骤，确保文件系统在开始使用之前处于一个已知的空状态。
- **显示目录（dir）**: `dir`功能列出当前目录下的所有文件，并显示文件的名称和大小。它会遍历目录区，读取并显示所有标记为已使用的FCB（文件控制块）。`Find_File`函数用于根据文件名查找文件。
- **读文件（read）**: 读取文件时，`read`函数根据文件控制块中的起始块号，按照文件的长度从磁盘中读取文件内容，并将其加载到缓冲区。在此文件系统中，文件内容被分割为若干块进行存储，`fat_buffer`用于保存文件各块的链接。
- **写文件（write）**: 写文件功能将数据写入文件末尾，`write`函数通过检查文件是否有足够的空间，判断是否需要分配新的磁盘块，并将数据追加到文件中。
- **创建文件（create）**: `create`函数通过查找空闲目录项，分配必要的磁盘块，并将文件的相关信息写入FCB和FAT表。此操作前提是磁盘上有足够的空闲空间。
- **删除文件（del）**: 删除文件时，`del`函数会释放文件占用的磁盘块，并将文件的FCB标记为未使用。FAT表也会更新，表示这些块已重新变为可用状态。

> 文件系统的磁盘结构与管理方法

文件系统模拟了一个简化的磁盘结构，包含以下几个主要区域：

1. **超级块（Super Block）**: 存储文件系统的元数据，如磁盘总容量、空闲容量、FAT表位置、数据区起始位置等。
2. **文件分配表（FAT）**: 用于跟踪文件的存储块链表，表示每个磁盘块的使用状态。FAT表用于管理文件块之间的链接。
3. **目录区**: 存储所有文件的控制信息（FCB），每个文件一个FCB，包含文件的元数据（如文件名、文件大小、起始块等）。
4. **数据区**: 存储实际的文件数据。每个文件的数据块通过FAT表链接在一起。

文件系统的磁盘结构包括了文件系统的元数据（超级块、FAT表等）和用户数据（文件的实际内容）。

> 空闲空间管理

- 空闲磁盘块的管理通过FAT表实现。FAT表中每个元素表示一个磁盘块的使用状态，0表示空闲，-1表示已分配，其他值表示块链的下一个块。
- 磁盘块的分配和回收通过`Get_Block`和`Put_Block`函数来管理。

#### **代码分析**

1. **格式化过程**:
    
    - 初始化超级块。
    - 清空目录区并初始化FCB。
    - 初始化FAT表，标记空闲的磁盘块。
    - 更新磁盘文件。

```cpp
void Real_Format()

{

    unsigned long int bcount;

     long int fatval,i;

    char *c;

  

    //更改系统超级块信息  

  FsSupBlk.fs_totalsize=total_disk_size;    

  FsSupBlk.fs_blocksize=block_size;

  FsSupBlk.fs_dir_start=1;  

  FsSupBlk.fs_dir_size=total_dir_size;

  

  FsSupBlk.fs_fat_start=total_dir_size+1;  

  FsSupBlk.fs_fat_size=total_fat_size;  

  FsSupBlk.fs_data_start=FsSupBlk.fs_fat_start+FsSupBlk.fs_fat_size;

  FsSupBlk.fs_data_size = FsSupBlk.fs_totalsize - FsSupBlk.fs_dir_size - FsSupBlk.fs_fat_size-1;

  FsSupBlk.fs_freesize= FsSupBlk.fs_data_size;

  

  Save_Boot();

    //初始化目录  

    for(i=0;i<128;i++) filefcb[i].flag=-1;          //为-1表示FCB未使用

    fseek(fsPtr,512L,SEEK_SET);

    fwrite(&filefcb[0],sizeof(struct FCB_Block),128,fsPtr);

  

      //初始化FAT

      fatval=FsSupBlk.fs_fat_start*512;

      fseek(fsPtr,fatval,SEEK_SET);     //定位文件指针  

      bcount=FsSupBlk.fs_fat_size+FsSupBlk.fs_dir_size+1;

      for(i=0;i<bcount;i++) fat_buffer[i]=-1;   //标记已经使用的磁盘数据块，即FAT区、目录区和启动区

      for(;i<FsSupBlk.fs_totalsize;i++) fat_buffer[i]=0;   //为0表示为空的物理快

      fwrite(&fat_buffer[0],sizeof(long int),FsSupBlk.fs_totalsize,fsPtr);

  

      LoadFat();

      //初始化数据区

      for(i=0;i<512;i++) rw_buffer[i]=' ';//缓冲区清空

      for(i=FsSupBlk.fs_data_start;i<FsSupBlk.fs_totalsize;i++)

           Write_Block(i,rw_buffer);  //缓冲区写入第i块
}
```
2. **文件创建过程**:
    
    - 检查磁盘空间。
    - 查找目录区空闲项，分配文件空间。
    - 更新FAT表和FCB。
```cpp
void create(char *fname,long int num)  //在当前目录下创建一个名字为str的文件，长度为num

{

    int i,j;      //true表示没有与该名字重名的文件

    int tempnode;

    long int pos_dir,getnum=0;

    unsigned long int blkcount;

  

    blkcount= num/512+1; //计算需要的物理块

    if(FsSupBlk.fs_freesize < blkcount) //磁盘没有足够空间

    {

        printf("\n 磁盘没有足够空间，不能建立 ！\n\n");

        return;

    }

  

    tempnode=Find_File(fname);

  

    if (tempnode!=-1) //表示文件存在

    { printf("\n 文件已经存在，不需要建立 ！\n\n"); return;}

  
  

    //  建立文件的处理

    pos_dir=FsSupBlk.fs_dir_start*FsSupBlk.fs_blocksize;

    fseek(fsPtr,pos_dir,SEEK_SET);  //定位到目录区

    for(i=0; i<FsSupBlk.fs_dir_size; i++)  

    {  

        //Read_Block(i+FsSupBlk.fs_dir_start,(char *)filefcb);

  

        fread(&filefcb[0],sizeof(struct FCB_Block),16 ,fsPtr);  

        for(j=0;j<16;j++)

            if(filefcb[j].flag == -1) //找到空目录项

            {

                // 分配空间, 标记FCB数据项，并将FCB写磁盘

  

                getnum=Get_Block(blkcount);

                if(getnum==-1){ printf("不能分配存储空间 \n");return;}

  

                filefcb[j].file_start=getnum;

                filefcb[j].flag = 1;

                filefcb[j].file_length=num;

                strcpy(filefcb[j].file_name,fname);

  

                //filefcb[].file_time=

                //filefcb[].file_date=

                // 改变磁盘FCB值

                pos_dir=pos_dir+sizeof(struct FCB_Block)*(i*16+j);

                fseek(fsPtr,pos_dir,SEEK_SET);            //定位到目录区的FCB项

                fwrite(&filefcb[j],sizeof(struct FCB_Block),1 ,fsPtr);

  

                //Write_Block(i+FsSupBlk.fs_dir_start,(char *)filefcb);

  

                printf("  文件占用了 %d 个物理块\n",blkcount);

                printf("  系统还有 %ld 个物理块可用\n\n",FsSupBlk.fs_freesize);

                return;

            }      

    }

    //没有FCB项，不能建立文件

    cout<<"当前没有足够的目录区，不能建立文件 ! "<<endl;

    return;  

}

```
3. **写文件过程**:
    
    - 查找文件。
    - 检查文件空间是否足够。
    - 如果不足，分配新块并更新FAT表。
    - 写入数据并更新FCB。

```cpp
void write(char *fname ,int num)//写文件,仅实现在文件尾部填加内容，并限制在512个字节内

{

    int tempnode;

    int i,j,x;

    int rescount;

    long int pos_dir;

  

      if (num > 512)              

      {

          printf("\n 不能写大于512字节的数据 ！\n\n");

          return;

      }

      tempnode=Find_File(fname);

      if (tempnode==-1)               //表示文件不存在

      {

          printf("\n 文件不存在，不能写 ！\n\n");

          return;

      }

    //  写文件的处理

  

    char *string=new char [num]; //申请空间

    for(i=0;i<num;i++)

        {

           cin>>string[i];      

        }

    rescount=filefcb[tempnode].file_length % FsSupBlk.fs_blocksize;

    if (num> FsSupBlk.fs_blocksize-rescount)

    {

        if (FsSupBlk.fs_freesize<1)

        {

            printf("\n 文件系统没有足够空间，不能写 ！\n\n");

            return;

        }

    }

  

    for(j=filefcb[tempnode].file_start;fat_buffer[j]!=-1;j=fat_buffer[j]);

    Read_Block(j,rw_buffer);

    if (num<= FsSupBlk.fs_blocksize-rescount)

    {

        for(i=0;i<num;i++)  rw_buffer[rescount+i]=string[i];

        Write_Block(j,rw_buffer);

    }

    else

    {

       for(i=0;i<FsSupBlk.fs_blocksize-rescount;i++)

            rw_buffer[rescount+i]=string[i];

       Write_Block(j,rw_buffer);

       fat_buffer[j]=Get_Block(1);

       j=fat_buffer[j];

       for(x=0;x<FsSupBlk.fs_blocksize;x++) rw_buffer[x]=' ';

       for(x=0;x<num-(FsSupBlk.fs_blocksize-rescount);x++)

            rw_buffer[x]=string[i+x];

       Write_Block(j,rw_buffer);

    }

    //delete []string;

    // 修改FCB项并写回磁盘

    filefcb[tempnode].file_length+=num;     // 增加文件的长度

    pos_dir=FsSupBlk.fs_dir_start*FsSupBlk.fs_blocksize+((find_fcb-1)*16+tempnode)*sizeof(struct FCB_Block);

    fseek(fsPtr,pos_dir,SEEK_SET);  //定位到目录区

    fwrite(&filefcb[tempnode],sizeof(struct FCB_Block),1 ,fsPtr);

    cin.ignore(10000,'\n'); //清除输入流缓冲区

    cout<<endl<<"=================== 写文件完成！=============="<<endl;  

    return;

}

```
4. **文件读取过程**:
    
    - 查找文件。
    - 根据文件的起始块读取数据。
```cpp
void read(char *fname,long start,int count)//读文件 ，限制在512个字节内

{

    int tempnode;

    int stblknum,offset;

    int dspnum;

    long i,j,x;

  

      tempnode=Find_File(fname);    

      if (tempnode==-1)              //表示文件不存在

      { printf("\n 文件不存在，不能读 ！\n\n");

        return;  }

  

      if (start > filefcb[tempnode].file_length)  //读的数据超出文件范围

      { printf("\n 超出文件范围，不能读 ！\n\n");

        return;  }

      //  读文件的处理

   printf("\n========================================\n");

    stblknum=start/FsSupBlk.fs_blocksize+1; // 起始物理块

    offset=start%FsSupBlk.fs_blocksize;     // 起始物理块的偏移量

    if(start+count>filefcb[tempnode].file_length )

         count= filefcb[tempnode].file_length-start;

  

    for(i=filefcb[tempnode].file_start,j=1;j<stblknum;j++)

        i=fat_buffer[i];

    Read_Block(i,rw_buffer);

    if(start+count>filefcb[tempnode].file_length )

         count= filefcb[tempnode].file_length-start;

    if(count<=FsSupBlk.fs_blocksize-offset)

    {

        for(j=0;j<count;j++) cout<<rw_buffer[j+offset];

    }

    else

    {

      for(j=0;j<FsSupBlk.fs_blocksize-offset;j++) cout<<rw_buffer[j+offset];

      dspnum=(count-(FsSupBlk.fs_blocksize-offset))/FsSupBlk.fs_blocksize+1;

      for(j=0;j<dspnum-1;j++)

      {  

          i=fat_buffer[i];

          Read_Block(i,rw_buffer);

          for(x=0;x<FsSupBlk.fs_blocksize;x++)

              cout<<rw_buffer[x];

      }

      i=fat_buffer[i];

      Read_Block(i,rw_buffer);

      x=(count-(FsSupBlk.fs_blocksize-offset))%FsSupBlk.fs_blocksize;

      for(j=0;j<x;j++) cout<<rw_buffer[x];

    }

    cout<<endl<<"============= 读文件完成！================"<<endl;  

    return;

}
```
---

### **总结**

本实验通过模拟文件系统的实现，深入展示了文件系统的核心概念和工作机制。通过分析代码实现，我们理解了文件系统的基本架构和关键组件：

**核心数据结构分析：**
- **FCB（文件控制块）**：作为文件系统的核心数据结构，FCB存储了文件的元数据信息，包括文件名、文件大小、创建时间、起始块号等。FCB是连接文件逻辑概念和物理存储的关键桥梁，通过FCB可以快速定位文件在磁盘上的位置，实现文件的快速访问和管理。
- **超级块（Super Block）**：存储文件系统的全局信息，包括磁盘总容量、空闲块数量、各区域起始位置等。
- **FAT表（文件分配表）**：实现文件的链式存储管理，通过FAT表可以追踪文件的各个数据块，同时用于空闲空间管理。

**文件系统功能实现：**
通过分析代码，我们了解了文件系统的基本操作实现原理：
- **格式化（format）**：初始化磁盘结构，建立文件系统的基本框架
- **文件创建（create）**：分配FCB项和磁盘块，建立文件的逻辑和物理映射
- **文件读写（read/write）**：通过FCB定位文件位置，利用FAT表进行数据块的链式访问
- **目录管理（dir）**：遍历FCB数组，显示文件信息

**实验收获：**
通过本实验，我深刻理解了FCB在文件系统中的核心地位。FCB不仅是文件系统进行文件管理的基础，更是理解整个文件系统工作原理的关键。掌握了FCB的概念和作用，就能更好地理解文件系统如何实现文件的组织、存储和访问。



