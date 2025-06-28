# title: 
# Lab2: ISAs Simulatio : x86 and ARM
-----------
# Target

a) Use more command lines or tools of Multi2Sim platform to get more simulation results about ISA such as disassembler, functional simulation, detailed simulation;
b) Understand ISA details based on the simulations of both ARM and x86, including the similar or different features of them.
# Process
## fuctional 
![Pasted image](<resources/Pasted image 20240330182808.png>)
## detailed
![Pasted image](<resources/Pasted image 20240330182746.png>)
>  Explanation as follows:

1. **Real Time**: The actual time spent executing the simulation, here it is 1.90 seconds.
2. **SimTime**: The simulation time inside the simulator, here it is 454272.00 nanoseconds (ns).
3. **Frequency**: The CPU frequency being simulated, here it is 1000 Megahertz (MHz).
4. **Cycles**: The number of CPU cycles completed during the simulation, here 454273.
5. **Instructions**: The total number of instructions executed, here 200432.
6. **InstructionsPerSecond**: The number of instructions the simulator executes per second, here 105645.
7. **CPI (Cycles Per Instruction)**: The average number of cycles per instruction, which can be calculated by dividing the total number of cycles by the number of instructions, not directly shown here but can be calculated as 454273 / 200432 ≈ 2.27.
8. **Committed Instructions**: The actual number of instructions that were completed, here 94949.
9. **CommittedInstructionsPerCycle**: The number of instructions completed per cycle, here 0.209.
10. **CommittedMicroInstructionsPerCycle**: The number of micro-instructions completed per cycle, here 0.3487.
11. **BranchPredictionAccuracy**: The accuracy of branch prediction, here 86.5%.

## sort.c
![Pasted image](<resources/Pasted image 20240331115804.png>)
![Pasted image](<resources/Pasted image 20240331115813.png>)
```bash
arm-linux-gnueabihf-gcc -o mysort sort.c
```
> cross-compiling-x86
```bash
ca@ca-virtual-machine:~/multi2sim-5.0$ m2s --x86-disasm mysort

; Multi2Sim 5.0 - A Simulation Framework for CPU-GPU Heterogeneous Computing
; Please use command 'm2s --help' for a list of command-line options.
; Simulation alpha-numeric ID: FMaET

Disassembly of section .init:

000082c8 <$a>:
    82c8:       08 40 2d                or     BYTE PTR [eax+0x2d],al
    82cb:       e9 1d 00 00 eb          jmp    eb0082ed

000082d0 <$a>:
    82d0:       08 80 bd e8 04 e0       or     BYTE PTR [eax-0x1ffb1743],al

Disassembly of section .plt:

000082d4 <$a>:
    82d4:       04 e0                   add    al,0xe0
    82d6:       2d e5 04 e0 9f          sub    eax,0x9fe004e5
    82db:                               ???
    82dc:                               ???
    82dd:                               ???
    82de:                               ???
    82df:                               ???
    82e0:       08 f0                   or     al,dh
    82e2:       be e5 1c 8d 00          mov    esi,0x8d1ce5
    82e7:       00 00                   add    BYTE PTR [eax],al
    82e9:                               ???
    82ea:                               ???
    82eb:                               ???
    82ec:       08 ca                   or     dl,cl
    82ee:       8c e2                   mov    edx,fs
    82f0:       1c fd                   sbb    al,0xfd
    82f2:       bc e5 00 c6 8f          mov    esp,0x8fc600e5
    82f7:                               ???
    82f8:       08 ca                   or     dl,cl
    82fa:       8c e2                   mov    edx,fs
    82fc:       14 fd                   adc    al,0xfd
    82fe:       bc e5 00 c6 8f          mov    esp,0x8fc600e5
    8303:                               ???
    8304:       08 ca                   or     dl,cl
    8306:       8c e2                   mov    edx,fs
    8308:       0c fd                   or     al,0xfd
    830a:       bc e5 00 c6 8f          mov    esp,0x8fc600e5
    830f:                               ???
    8310:       08 ca                   or     dl,cl
    8312:       8c e2                   mov    edx,fs
    8314:       04 fd                   add    al,0xfd
    8316:       bc e5 4f f0 00          mov    esp,0xf04fe5

Disassembly of section .text:

00008318 <$t>:
    8318:       4f                      dec    edi

00008319 <_start>:
    8319:       f0 00 0b                add    BYTE PTR [ebx],cl
    831c:       4f                      dec    edi
    831d:       f0 00 0e                add    BYTE PTR [esi],cl
    8320:       02 bc 6a 46 04 b4 01    add    bh,BYTE PTR [edx+ebp*2+0x1b40446]
    8327:       b4 df                   mov    ah,0xdf
    8329:                               ???
    832a:       10 c0                   adc    al,al
    832c:       4d                      dec    ebp
    832d:                               ???
    832e:       04 cd                   add    al,0xcd
    8330:       03 48 04                add    ecx,DWORD PTR [eax+0x4]
    8333:       4b                      dec    ebx
    8334:       ff f7                   push   edi
    8336:       de ef                   fsubp  st(7),st
    8338:       ff f7                   push   edi
    833a:       e8 ef 21 85 00          call   85a52e
    833f:       00 f5                   add    ch,dh
    8341:       83 00 00                add    DWORD PTR [eax],0x0
    8344:                               ???
    8345:       84 00                   test   BYTE PTR [eax],al
    8347:       00 14 30                add    BYTE PTR [eax+esi],dl
    834a:                               ???
    834b:                               ???
    834c:       14 20                   adc    al,0x20
    834e:                               ???
    834f:                               ???
    8350:       03 30                   add    esi,DWORD PTR [eax]
    8352:                               ???
    8353:                               ???
    8354:       02 20                   add    ah,BYTE PTR [eax]
    8356:       93                      xchg   ebx,eax
    8357:       e7 00                   out    0x0,eax
    8359:       00 52 e3                add    BYTE PTR [edx-0x1d],dl
    835c:                               ???
    835d:                               ???
    835e:                               ???
    835f:       01 e6                   add    esi,esp
    8361:                               ???
    8362:                               ???
    8363:                               ???

00008364 <$d>:
    8364:       a8 8c                   test   al,0x8c
    8366:       00 00                   add    BYTE PTR [eax],al
    8368:       1c 00                   sbb    al,0x0
    836a:       00 00                   add    BYTE PTR [eax],al

0000836c <$t>:
    836c:                               ???

0000836d <deregister_tm_clones>:
    836d:       4b                      dec    ebx
    836e:       41                      inc    ecx
    836f:                               ???
    8370:       28 00                   sub    BYTE PTR [eax],al
    8372:                               ???
    8373:                               ???
    8374:       01 00                   add    DWORD PTR [eax],eax
    8376:       1b 1a                   sbb    ebx,DWORD PTR [edx]
    8378:                               ???
    8379:       2b 00                   sub    eax,DWORD PTR [eax]
    837b:       d8 70 47                fdiv   DWORD PTR [eax+0x47]
    837e:       40                      inc    eax
    837f:                               ???
    8380:       00 03                   add    BYTE PTR [ebx],al
    8382:                               ???
    8383:                               ???
    8384:       00 03                   add    BYTE PTR [ebx],al
    8386:       00 2b                   add    BYTE PTR [ebx],ch
    8388:                               ???
    8389:       d0 18                   rcr    BYTE PTR [eax],1
    838b:       47                      inc    edi

0000838c <$d>:
    838c:       2b 10                   sub    edx,DWORD PTR [eax]
    838e:       01 00                   add    DWORD PTR [eax],eax

00008390 <$t>:
    8390:       41                      inc    ecx

00008391 <register_tm_clones>:
    8391:                               ???
    8392:       28 03                   sub    BYTE PTR [ebx],al
    8394:       41                      inc    ecx
    8395:                               ???
    8396:       28 00                   sub    BYTE PTR [eax],al
    8398:                               ???
    8399:                               ???
    839a:       01 03                   add    DWORD PTR [ebx],eax
    839c:                               ???
    839d:                               ???
    839e:       01 00                   add    DWORD PTR [eax],eax
    83a0:       1b 1a                   sbb    ebx,DWORD PTR [edx]
    83a2:                               ???
    83a3:       10 03                   adc    BYTE PTR [ebx],al
    83a5:       eb d3                   jmp    837a
    83a7:       73 59                   jae    8402
    83a9:       10 00                   adc    BYTE PTR [eax],al
    83ab:                               ???
    83ac:       70 47                   jo     83f5
    83ae:       40                      inc    eax
    83af:                               ???
    83b0:       00 02                   add    BYTE PTR [edx],al
    83b2:                               ???
    83b3:                               ???
    83b4:       00 02                   add    BYTE PTR [edx],al
    83b6:       00 2a                   add    BYTE PTR [edx],ch
    83b8:                               ???
    83b9:       d0 10                   rcl    BYTE PTR [eax],1
    83bb:       47                      inc    edi
    83bc:       10 b5 41 f2 28 04       adc    BYTE PTR [ebp+0x428f241],dh
    83c2:                               ???
    83c3:                               ???
    83c4:       01 04 23                add    DWORD PTR [ebx],eax
    83c7:       78 1b                   js     83e4
    83c9:       b9 ff f7 cf ff          mov    ecx,0xffcff7ff
    83ce:       01 23                   add    DWORD PTR [ebx],esp
    83d0:       23 70 10                and    esi,DWORD PTR [eax+0x10]
    83d3:       bd 40 f6 14 70          mov    ebp,0x7014f640
    83d8:                               ???
    83d9:                               ???
    83da:       01 00                   add    DWORD PTR [eax],eax
    83dc:       08 b5 03 68 2b b1       or     BYTE PTR [ebp-0x4ed497fd],dh
    83e2:       40                      inc    eax
    83e3:                               ???
    83e4:       00 03                   add    BYTE PTR [ebx],al
    83e6:                               ???
    83e7:                               ???
    83e8:       00 03                   add    BYTE PTR [ebx],al
    83ea:       03 b1 98 47 bd e8       add    esi,DWORD PTR [ecx-0x1742b868]
    83f0:       08 40 cd                or     BYTE PTR [eax-0x33],al
    83f3:       e7 b0                   out    0xb0,eax

000083f5 <main>:
    83f5:       b5 8a                   mov    ch,0x8a
    83f7:       b0 00                   mov    al,0x0
    83f9:       af                      scasd
    83fa:       48                      dec    eax
    83fb:                               ???
    83fc:       34 53                   xor    al,0x53
    83fe:                               ???
    83ff:                               ???
    8400:       00 03                   add    BYTE PTR [ebx],al
    8402:                               ???
    8403:                               ???
    8404:       14 04                   adc    al,0x4
    8406:       1d 46 0f cd 0f          sbb    eax,0xfcd0f46
    840b:                               ???
    840c:       2b 68 23                sub    ebp,DWORD PTR [eax+0x23]
    840f:                               ???
    8410:       05 23 fb 60 00          add    eax,0x60fb23
    8415:       23 3b                   and    edi,DWORD PTR [ebx]
    8417:                               ???
    8418:       40                      inc    eax
    8419:                               ???
    841a:       00 23                   add    BYTE PTR [ebx],ah
    841c:       7b 60                   jnp    847e
    841e:       33 e0                   xor    esp,eax
    8420:       7b 68                   jnp    848a
    8422:                               ???
    8423:       00 07                   add    BYTE PTR [edi],al
    8425:                               ???
    8426:       28 01                   sub    BYTE PTR [ecx],al
    8428:       0b 44 53 f8             or     eax,DWORD PTR [ebx+edx*2-0x8]
    842c:       14 2c                   adc    al,0x2c
    842e:       7b 68                   jnp    8498
    8430:       01 33                   add    DWORD PTR [ebx],esi
    8432:                               ???
    8433:       00 07                   add    BYTE PTR [edi],al
    8435:                               ???
    8436:       28 01                   sub    BYTE PTR [ecx],al
    8438:       0b 44 53 f8             or     eax,DWORD PTR [ebx+edx*2-0x8]
    843c:       14 3c                   adc    al,0x3c
    843e:                               ???
    843f:       42                      inc    edx
    8440:                               ???
    8441:                               ???
    8442:       7b 68                   jnp    84ac
    8444:                               ???
    8445:       00 07                   add    BYTE PTR [edi],al
    8447:                               ???
    8448:       28 02                   sub    BYTE PTR [edx],al
    844a:       13 44 53 f8             adc    eax,DWORD PTR [ebx+edx*2-0x8]
    844e:       14 3c                   adc    al,0x3c
    8450:       3b 61 7b                cmp    esp,DWORD PTR [ecx+0x7b]
    8453:       68 01 33 9b 00          push   0x9b3301
    8458:                               ???
    8459:                               ???
    845a:       28 01                   sub    BYTE PTR [ecx],al
    845c:       0b 44 53 f8             or     eax,DWORD PTR [ebx+edx*2-0x8]
    8460:       14 2c                   adc    al,0x2c
    8462:       7b 68                   jnp    84cc
    8464:                               ???
    8465:       00 07                   add    BYTE PTR [edi],al
    8467:                               ???
    8468:       28 01                   sub    BYTE PTR [ecx],al
    846a:       0b 44 43 f8             or     eax,DWORD PTR [ebx+eax*2-0x8]
    846e:       14 2c                   adc    al,0x2c
    8470:       7b 68                   jnp    84da
    8472:       01 33                   add    DWORD PTR [ebx],esi
    8474:                               ???
    8475:       00 07                   add    BYTE PTR [edi],al
    8477:                               ???
    8478:       28 02                   sub    BYTE PTR [edx],al
    847a:       13 44 3a 69             adc    eax,DWORD PTR [edx+edi+0x69]
    847e:       43                      inc    ebx
    847f:                               ???
    8480:       14 2c                   adc    al,0x2c
    8482:       7b 68                   jnp    84ec
    8484:       01 33                   add    DWORD PTR [ebx],esi
    8486:       7b 60                   jnp    84e8
    8488:                               ???
    8489:       68 3b 68 d3 1a          push   0x1ad3683b
    848e:       5a                      pop    edx
    848f:                               ???
    8490:       7b 68                   jnp    84fa
    8492:                               ???
    8493:       42                      inc    edx
    8494:                               ???
    8495:       dc 3b                   fdivr  QWORD PTR [ebx]
    8497:       68 01 33 3b 60          push   0x603b3301
    849c:                               ???
    849d:       68 5a 1e 3b 68          push   0x683b1e5a
    84a2:                               ???
    84a3:       42                      inc    edx
    84a4:       b9 dc 00 23 bb          mov    ecx,0xbb2300dc
    84a9:                               ???
    84aa:       10 e0                   adc    al,ah
    84ac:       bb 68 9b 00 07          mov    ebx,0x7009b68
    84b1:                               ???
    84b2:       28 01                   sub    BYTE PTR [ecx],al
    84b4:       0b 44 53 f8             or     eax,DWORD PTR [ebx+edx*2-0x8]
    84b8:       14 3c                   adc    al,0x3c
    84ba:       48                      dec    eax
    84bb:                               ???
    84bc:       30 50 c0                xor    BYTE PTR [eax-0x40],dl
    84bf:                               ???
    84c0:       00 00                   add    BYTE PTR [eax],al
    84c2:       19 46 ff                sbb    DWORD PTR [esi-0x1],eax
    84c5:       f7 10                   not    DWORD PTR [eax]
    84c7:       ef                      out    dx,eax
    84c8:       bb 68 01 33 bb          mov    ebx,0xbb330168
    84cd:                               ???
    84ce:       ba 68 fb 68 9a          mov    edx,0x9a68fb68
    84d3:       42                      inc    edx
    84d4:                               ???
    84d5:       db 00                   fild   DWORD PTR [eax]
    84d7:       23 18                   and    ebx,DWORD PTR [eax]
    84d9:       46                      inc    esi
    84da:       28 37                   sub    BYTE PTR [edi],dh
    84dc:       bd 46 b0 bd 2d          mov    ebp,0x2dbdb046

000084e1 <__libc_csu_init>:
    84e1:       e9 f8 43 07 46          jmp    4607c8de
    84e6:       0c 4e                   or     al,0x4e
    84e8:       88 46 0c                mov    BYTE PTR [esi+0xc],al
    84eb:       4d                      dec    ebp
    84ec:       91                      xchg   ecx,eax
    84ed:       46                      inc    esi
    84ee:       7e 44                   jle    8534
    84f0:       ff f7                   push   edi
    84f2:                               ???
    84f3:       ee                      out    dx,al
    84f4:       7d 44                   jge    853a
    84f6:       76 1b                   jbe    8513
    84f8:       b6 10                   mov    dh,0x10
    84fa:       0a d0                   or     dl,al
    84fc:       04 3d                   add    al,0x3d
    84fe:       00 24 01                add    BYTE PTR [ecx+eax],ah
    8501:       34 55                   xor    al,0x55
    8503:                               ???
    8504:       04 3f                   add    al,0x3f
    8506:       38 46 41                cmp    BYTE PTR [esi+0x41],al
    8509:       46                      inc    esi
    850a:       4a                      dec    edx
    850b:       46                      inc    esi
    850c:       98                      cwde
    850d:       47                      inc    edi
    850e:       b4 42                   mov    ah,0x42
    8510:       f6 d1                   not    cl
    8512:       bd e8 f8 83 00          mov    ebp,0x83f8e8
    8517:       bf 1e 8a 00 00          mov    edi,0x8a1e
    851c:       14 8a                   adc    al,0x8a
    851e:       00 00                   add    BYTE PTR [eax],al

00008520 <$t>:
    8520:       70 47                   jo     8569
    8522:       00 bf 08 40 2d e9       add    BYTE PTR [edi-0x16d2bff8],bh

Disassembly of section .fini:

00008524 <$a>:
    8524:       08 40 2d                or     BYTE PTR [eax+0x2d],al
    8527:       e9 08 80 bd e8          jmp    e8be0534
```

> cross-compiling-arm
```bash
ca@ca-virtual-machine:~/multi2sim-5.0$ m2s --arm-disasm mysort

; Multi2Sim 5.0 - A Simulation Framework for CPU-GPU Heterogeneous Computing
; Please use command 'm2s --help' for a list of command-line options.
; Simulation alpha-numeric ID: wlkPE

    8318:       0b00f04f        bleq 4445c
    831c:       0e00f04f        ???
    8320:       466abc02        strbtmi r11, [r10, -r2, lsl #24]
    8324:       b401b404        strlt r11, [r1, #-1028] 
    8328:       c010f8df        ldrsbgt pc, [r0, -r15]
    832c:       cd04f84d        ???
    8330:       4b044803        blmi 11a344
    8334:       efdef7ff        svc  0xdef7ff
    8338:       efe8f7ff        svc  0xe8f7ff
    833c:       00008521        .word   0x00008521
    8340:       000083f5        .word   0x000083f5
    8344:       000084e1        .word   0x000084e1

00008348 <call_weak_fn>
    8348:       e59f3014        ldr  r3, [r15, #20] 
    834c:       e59f2014        ldr  r2, [r15, #20] 
    8350:       e08f3003        add  r3, pc, r3 , LSL #0   ;0x0
    8354:       e7932002        ldr  r2, [r3, r2, lsl #0] 
    8358:       e3520000        cmp  r2, #0   ;0x0
    835c:       012fff1e        bxeq lr
    8360:       eaffffe6        b  8300
    8364:       00008ca8        .word   0x00008ca8
    8368:       0000001c        .word   0x0000001c

0000836c <deregister_tm_clones>
    836c:       f2414b07                ldrr3,[pc,#28]
    836e:       f241 0028       movwr0,#4136    ; 0x1028
    8372:       f2c0 0001       movtr0,#1       ; 0x1
    8376:       2b061a1b                subsr3,r3,r3
    8378:       d8002b06                cmpr3,#6
    837a:       4770d800                bhi,837e
    837c:       f2404770                bxlr
    837e:       f240 0300       movwr3,#0       ; 0x0
    8382:       f2c0 0300       movtr3,#0       ; 0x0
    8386:       d0f82b00                cmpr3,#0
    8388:       4718d0f8                beq,837c
    838a:       102b4718                bxr3
    838c:       1102b           .word   0x0001102b
    838e:       f2410001                .word   0xf2410001

00008390 <register_tm_clones>
    8390:       f241 0328       movwr3,#4136    ; 0x1028
    8394:       f241 0028       movwr0,#4136    ; 0x1028
    8398:       f2c0 0301       movtr3,#1       ; 0x1
    839c:       f2c0 0001       movtr0,#1       ; 0x1
    83a0:       109b1a1b                subsr3,r3,r3
    83a2:       eb03109b                asrsr3,r3,#2
    83a4:       eb03 73d3       add.wr3,r3,r3{lsr #31}
    83a8:       d1001059                asrsr1,r3,#1
    83aa:       4770d100                bne,83ae
    83ac:       f2404770                bxlr
    83ae:       f240 0200       movwr2,#0       ; 0x0
    83b2:       f2c0 0200       movtr2,#0       ; 0x0
    83b6:       d0f82a00                cmpr2,#0
    83b8:       4710d0f8                beq,83ac
    83ba:       b5104710                bxr2

000083bc <__do_global_dtors_aux>
    83bc:       f241b510                push,{r4 r14 }
    83be:       f241 0428       movwr4,#4136    ; 0x1028
    83c2:       f2c0 0401       movtr4,#1       ; 0x1
    83c6:       b91b7823                ldrbr3,[r4,#0]
    83c8:       f7ffb91b                cbnz,r3,83d2
    83ca:       f7ff ffcf       bl#33642        ; 0x836a
    83ce:       70232301                movsr3,#1
    83d0:       bd107023                strbr3,[r4,#0]
    83d2:       f640bd10                pop{r4 r14 }

000083d4 <frame_dummy>
    83d4:       f640 7014       movwr0,#3860    ; 0xf14
    83d8:       f2c0 0001       movtr0,#1       ; 0x1
    83dc:       6803b508                push,{r3 r14 }
    83de:       b12b6803                ldrr3,[r0,#0]
    83e0:       f240b12b                cbz,r3,83ee
    83e2:       f240 0300       movwr3,#0       ; 0x0
    83e6:       f2c0 0300       movtr3,#0       ; 0x0
    83ea:       4798b103                cbz,r3,83ec
    83ec:       e8bd4798                blxr3
    83ee:       e8bd 4008       pop.w{r3 r14 }
    83f2:       b5b0e7cd                b,8390

000083f4 <main>
    83f4:       b08ab5b0                push,{r4 r5 r7 r14 }
    83f6:       af00b08a                subsp,sp,#40
    83f8:       f248af00                addr7,sp,#0
    83fa:       f248 5334       movwr3,#34100   ; 0x8534
    83fe:       f2c0 0300       movtr3,#0       ; 0x0
    8402:       f107 0414       add.wr4,r7,#20
    8406:       cd0f461d                movsr5,r3
    8408:       c40fcd0f                ldmr5!,{r0 r1 r2 r3 r14 }
    840a:       682bc40f                stmr4!,{r0 r1 r2 r3 }
    840c:       6023682b                ldrr3,[r5,#0]
    840e:       23056023                strr3,[r4,#0]
    8410:       60fb2305                movsr3,#5
    8412:       230060fb                strr3,[r7,#12]
    8414:       603b2300                movsr3,#0
    8416:       e040603b                strr3,[r7,#0]
    8418:       2300e040                b,849c
    841a:       607b2300                movsr3,#0
    841c:       e033607b                strr3,[r7,#4]
    841e:       687be033                b,8488
    8420:       9b687b          ldrr3,[r7,#4]
    8422:       f107009b                lslsr3,r3,#2
    8424:       f107 0128       add.wr1,r7,#40
    8428:       f853440b                addsr3,r1,r1
    842a:       f853 2c14       ldr.wr2,[r3,{[#-20]}]
    842e:       3301687b                ldrr3,[r7,#4]
    8430:       9b3301          addsr3,r3,#1
    8432:       f107009b                lslsr3,r3,#2
    8434:       f107 0128       add.wr1,r7,#40
    8438:       f853440b                addsr3,r1,r1
    843a:       f853 3c14       ldr.wr3,[r3,{[#-20]}]
    843e:       dd1f429a                cmpr2,r3
    8440:       687bdd1f                ble,8482
    8442:       9b687b          ldrr3,[r7,#4]
    8444:       f107009b                lslsr3,r3,#2
    8446:       f107 0228       add.wr2,r7,#40
    844a:       f8534413                addsr3,r2,r2
    844c:       f853 3c14       ldr.wr3,[r3,{[#-20]}]
    8450:       687b613b                strr3,[r7,#16]
    8452:       3301687b                ldrr3,[r7,#4]
    8454:       9b3301          addsr3,r3,#1
    8456:       f107009b                lslsr3,r3,#2
    8458:       f107 0128       add.wr1,r7,#40
    845c:       f853440b                addsr3,r1,r1
    845e:       f853 2c14       ldr.wr2,[r3,{[#-20]}]
    8462:       9b687b          ldrr3,[r7,#4]
    8464:       f107009b                lslsr3,r3,#2
    8466:       f107 0128       add.wr1,r7,#40
    846a:       f843440b                addsr3,r1,r1
    846c:       f843 2c14       strtr2,[r3,[#-20]]
    8470:       3301687b                ldrr3,[r7,#4]
    8472:       9b3301          addsr3,r3,#1
    8474:       f107009b                lslsr3,r3,#2
    8476:       f107 0228       add.wr2,r7,#40
    847a:       693a4413                addsr3,r2,r2
    847c:       f843693a                ldrr2,[r7,#16]
    847e:       f843 2c14       strtr2,[r3,[#-20]]
    8482:       3301687b                ldrr3,[r7,#4]
    8484:       607b3301                addsr3,r3,#1
    8486:       68fa607b                strr3,[r7,#4]
    8488:       683b68fa                ldrr2,[r7,#12]
    848a:       1ad3683b                ldrr3,[r7,#0]
    848c:       1e5a1ad3                subsr3,r2,r2
    848e:       687b1e5a                subsr2,r3,#1
    8490:       429a687b                ldrr3,[r7,#4]
    8492:       dcc4429a                cmpr2,r3
    8494:       683bdcc4                bgt,8420
    8496:       3301683b                ldrr3,[r7,#0]
    8498:       603b3301                addsr3,r3,#1
    849a:       68fb603b                strr3,[r7,#0]
    849c:       1e5a68fb                ldrr3,[r7,#12]
    849e:       683b1e5a                subsr2,r3,#1
    84a0:       429a683b                ldrr3,[r7,#0]
    84a2:       dcb9429a                cmpr2,r3
    84a4:       2300dcb9                bgt,841a
    84a6:       60bb2300                movsr3,#0
    84a8:       e01060bb                strr3,[r7,#8]
    84aa:       68bbe010                b,84ce
    84ac:       9b68bb          ldrr3,[r7,#8]
    84ae:       f107009b                lslsr3,r3,#2
    84b0:       f107 0128       add.wr1,r7,#40
    84b4:       f853440b                addsr3,r1,r1
    84b6:       f853 3c14       ldr.wr3,[r3,{[#-20]}]
    84ba:       f248 5030       movwr0,#34096   ; 0x8530
    84be:       f2c0 0000       movtr0,#0       ; 0x0
    84c2:       f7ff4619                movsr1,r3
    84c4:       f7ff ef10       blx#33510       ; 0x82e6
    84c8:       330168bb                ldrr3,[r7,#8]
    84ca:       60bb3301                addsr3,r3,#1
    84cc:       68ba60bb                strr3,[r7,#8]
    84ce:       68fb68ba                ldrr2,[r7,#8]
    84d0:       429a68fb                ldrr3,[r7,#12]
    84d2:       dbea429a                cmpr2,r3
    84d4:       2300dbea                blt,84ac
    84d6:       46182300                movsr3,#0
    84d8:       37284618                movsr0,r3
    84da:       46bd3728                addsr7,r7,#40
    84dc:       bdb046bd                movsp,r7
    84de:       e92dbdb0                pop{r4 r5 r7 r14 }

000084e0 <__libc_csu_init>
    84e0:       e92d 43f8       push.w{r3 r4 r5 r6 r7 r8 r9 r14 }
    84e4:       4e0c4607                movsr7,r0
    84e6:       46884e0c                ldrr6,[pc,#48]
    84e8:       4d0c4688                movr8,r1
    84ea:       46914d0c                ldrr5,[pc,#48]
    84ec:       447e4691                movr9,r2
    84ee:       f7ff447e                addsr6,r7,r7
    84f0:       f7ff eeea       blx#33478       ; 0x82c6
    84f4:       1b76447d                addsr5,r7,r7
    84f6:       10b61b76                subsr6,r6,r6
    84f8:       d00a10b6                asrsr6,r6,#2
    84fa:       3d04d00a                beq,8512
    84fc:       24003d04                subsr5,r5,#4
    84fe:       34012400                movsr4,#0
    8500:       f8553401                addsr4,r4,#1
    8502:       f855 3f04       ldr.wr3,[r5,{[#4]!}]
    8506:       46414638                movsr0,r7
    8508:       464a4641                movsr1,r8
    850a:       4798464a                movsr2,r9
    850c:       42b44798                blxr3
    850e:       d1f642b4                cmpr4,r6
    8510:       e8bdd1f6                bne,8500
    8512:       e8bd 83f8       pop.w{r3 r4 r5 r6 r7 r8 r9 r15 }
    8516:       8a1ebf00                nop
    8518:       8a1e            .word   0x00008a1e
    851a:       8a140000                .word   0x8a140000
    851c:       8a14            .word   0x00008a14
    851e:       47700000                .word   0x47700000

00008520 <__libc_csu_fini>
    8520:       bf004770                bxlr
    8522:       4008bf00                nop

```
> x86-x86
```bash
ca@ca-virtual-machine:~/multi2sim-5.0$ m2s --x86-disasm sort

; Multi2Sim 5.0 - A Simulation Framework for CPU-GPU Heterogeneous Computing
; Please use command 'm2s --help' for a list of command-line options.
; Simulation alpha-numeric ID: MJ8gV

Disassembly of section .init:

080482b4 <_init>:
 80482b4:       53                      push   ebx
 80482b5:       83 ec 08                sub    esp,0x8
 80482b8:       e8 93 00 00 00          call   8048350
 80482bd:       81 c3 43 1d 00 00       add    ebx,0x1d43
 80482c3:       8b 83 fc ff ff ff       mov    eax,DWORD PTR [ebx-0x4]
 80482c9:       85 c0                   test   eax,eax
 80482cb:       74 05                   je     80482d2
 80482cd:       e8 2e 00 00 00          call   8048300
 80482d2:       83 c4 08                add    esp,0x8
 80482d5:       5b                      pop    ebx
 80482d6:       c3                      ret

Disassembly of section .plt:
 80482e0:       ff 35 04 a0 04 08       push   DWORD PTR ds:0x804a004
 80482e6:       ff 25 08 a0 04 08       jmp    DWORD PTR ds:0x804a008
 80482ec:       00 00                   add    BYTE PTR [eax],al
 80482ee:       00 00                   add    BYTE PTR [eax],al
 80482f0:       ff 25 0c a0 04 08       jmp    DWORD PTR ds:0x804a00c
 80482f6:       68 00 00 00 00          push   0x0
 80482fb:       e9 e0 ff ff ff          jmp    80482e0
 8048300:       ff 25 10 a0 04 08       jmp    DWORD PTR ds:0x804a010
 8048306:       68 08 00 00 00          push   0x8
 804830b:       e9 d0 ff ff ff          jmp    80482e0
 8048310:       ff 25 14 a0 04 08       jmp    DWORD PTR ds:0x804a014
 8048316:       68 10 00 00 00          push   0x10
 804831b:       e9 c0 ff ff ff          jmp    80482e0

Disassembly of section .text:

08048320 <_start>:
 8048320:       31 ed                   xor    ebp,ebp
 8048322:       5e                      pop    esi
 8048323:       89 e1                   mov    ecx,esp
 8048325:       83 e4 f0                and    esp,0xf0
 8048328:       50                      push   eax
 8048329:       54                      push   esp
 804832a:       52                      push   edx
 804832b:       68 90 85 04 08          push   0x8048590
 8048330:       68 20 85 04 08          push   0x8048520
 8048335:       51                      push   ecx
 8048336:       56                      push   esi
 8048337:       68 1d 84 04 08          push   0x804841d
 804833c:       e8 cf ff ff ff          call   8048310
 8048341:       f4                      hlt
 8048342:       66 90                   xchg   ax,ax
 8048344:       66 90                   xchg   ax,ax
 8048346:       66 90                   xchg   ax,ax
 8048348:       66 90                   xchg   ax,ax
 804834a:       66 90                   xchg   ax,ax
 804834c:       66 90                   xchg   ax,ax
 804834e:       66 90                   xchg   ax,ax

08048350 <__x86.get_pc_thunk.bx>:
 8048350:       8b 1c 24                mov    ebx,DWORD PTR [esp]
 8048353:       c3                      ret
 8048354:       66 90                   xchg   ax,ax
 8048356:       66 90                   xchg   ax,ax
 8048358:       66 90                   xchg   ax,ax
 804835a:       66 90                   xchg   ax,ax
 804835c:       66 90                   xchg   ax,ax
 804835e:       66 90                   xchg   ax,ax

08048360 <deregister_tm_clones>:
 8048360:       b8 23 a0 04 08          mov    eax,0x804a023
 8048365:       2d 20 a0 04 08          sub    eax,0x804a020
 804836a:       83 f8 06                cmp    eax,0x6
 804836d:       77 01                   ja     8048370
 804836f:       c3                      ret
 8048370:       b8 00 00 00 00          mov    eax,0x0
 8048375:       85 c0                   test   eax,eax
 8048377:       74 f6                   je     804836f
 8048379:       55                      push   ebp
 804837a:       89 e5                   mov    ebp,esp
 804837c:       83 ec 18                sub    esp,0x18
 804837f:       c7 04 24 20 a0 04 08    mov    DWORD PTR [esp],0x804a020
 8048386:       ff d0                   call   eax
 8048388:       c9                      leave
 8048389:       c3                      ret
 804838a:       8d b6 00 00 00 00       lea    esi,[esi]

08048390 <register_tm_clones>:
 8048390:       b8 20 a0 04 08          mov    eax,0x804a020
 8048395:       2d 20 a0 04 08          sub    eax,0x804a020
 804839a:       c1 f8 02                sar    eax,0x2
 804839d:       89 c2                   mov    edx,eax
 804839f:       c1 ea 1f                shr    edx,0x1f
 80483a2:       01 d0                   add    eax,edx
 80483a4:       d1 f8                   sar    eax,1
 80483a6:       75 01                   jne    80483a9
 80483a8:       c3                      ret
 80483a9:       ba 00 00 00 00          mov    edx,0x0
 80483ae:       85 d2                   test   edx,edx
 80483b0:       74 f6                   je     80483a8
 80483b2:       55                      push   ebp
 80483b3:       89 e5                   mov    ebp,esp
 80483b5:       83 ec 18                sub    esp,0x18
 80483b8:       89 44 24 04             mov    DWORD PTR [esp+0x4],eax
 80483bc:       c7 04 24 20 a0 04 08    mov    DWORD PTR [esp],0x804a020
 80483c3:       ff d2                   call   edx
 80483c5:       c9                      leave
 80483c6:       c3                      ret
 80483c7:       89 f6                   mov    esi,esi
 80483c9:       8d bc 27 00 00 00 00    lea    edi,[edi]

080483d0 <__do_global_dtors_aux>:
 80483d0:       80 3d 20 a0 04 08 00    cmp    BYTE PTR ds:0x804a020,0x0
 80483d7:       75 13                   jne    80483ec
 80483d9:       55                      push   ebp
 80483da:       89 e5                   mov    ebp,esp
 80483dc:       83 ec 08                sub    esp,0x8
 80483df:       e8 7c ff ff ff          call   8048360
 80483e4:       c6 05 20 a0 04 08 01    mov    BYTE PTR ds:0x804a020,0x1
 80483eb:       c9                      leave
 80483ec:       f3 c3                   repz   ret
 80483ee:       66 90                   xchg   ax,ax

080483f0 <frame_dummy>:
 80483f0:       a1 10 9f 04 08          mov    eax,ds:0x8049f10
 80483f5:       85 c0                   test   eax,eax
 80483f7:       74 1f                   je     8048418
 80483f9:       b8 00 00 00 00          mov    eax,0x0
 80483fe:       85 c0                   test   eax,eax
 8048400:       74 16                   je     8048418
 8048402:       55                      push   ebp
 8048403:       89 e5                   mov    ebp,esp
 8048405:       83 ec 18                sub    esp,0x18
 8048408:       c7 04 24 10 9f 04 08    mov    DWORD PTR [esp],0x8049f10
 804840f:       ff d0                   call   eax
 8048411:       c9                      leave
 8048412:       e9 79 ff ff ff          jmp    8048390
 8048417:       90                      nop
 8048418:       e9 73 ff ff ff          jmp    8048390

0804841d <main>:
 804841d:       55                      push   ebp
 804841e:       89 e5                   mov    ebp,esp
 8048420:       83 e4 f0                and    esp,0xf0
 8048423:       83 ec 40                sub    esp,0x40
 8048426:       c7 44 24 2c 04 00 00    mov    DWORD PTR [esp+0x2c],0x4
 804842d:       00 
 804842e:       c7 44 24 30 02 00 00    mov    DWORD PTR [esp+0x30],0x2
 8048435:       00 
 8048436:       c7 44 24 34 07 00 00    mov    DWORD PTR [esp+0x34],0x7
 804843d:       00 
 804843e:       c7 44 24 38 01 00 00    mov    DWORD PTR [esp+0x38],0x1
 8048445:       00 
 8048446:       c7 44 24 3c 03 00 00    mov    DWORD PTR [esp+0x3c],0x3
 804844d:       00 
 804844e:       c7 44 24 24 05 00 00    mov    DWORD PTR [esp+0x24],0x5
 8048455:       00 
 8048456:       c7 44 24 18 00 00 00    mov    DWORD PTR [esp+0x18],0x0
 804845d:       00 
 804845e:       eb 6e                   jmp    80484ce
 8048460:       c7 44 24 1c 00 00 00    mov    DWORD PTR [esp+0x1c],0x0
 8048467:       00 
 8048468:       eb 4a                   jmp    80484b4
 804846a:       8b 44 24 1c             mov    eax,DWORD PTR [esp+0x1c]
 804846e:       8b 54 84 2c             mov    edx,DWORD PTR [esp+eax*4+0x2c]
 8048472:       8b 44 24 1c             mov    eax,DWORD PTR [esp+0x1c]
 8048476:       83 c0 01                add    eax,0x1
 8048479:       8b 44 84 2c             mov    eax,DWORD PTR [esp+eax*4+0x2c]
 804847d:       39 c2                   cmp    edx,eax
 804847f:       7e 2e                   jle    80484af
 8048481:       8b 44 24 1c             mov    eax,DWORD PTR [esp+0x1c]
 8048485:       8b 44 84 2c             mov    eax,DWORD PTR [esp+eax*4+0x2c]
 8048489:       89 44 24 28             mov    DWORD PTR [esp+0x28],eax
 804848d:       8b 44 24 1c             mov    eax,DWORD PTR [esp+0x1c]
 8048491:       83 c0 01                add    eax,0x1
 8048494:       8b 54 84 2c             mov    edx,DWORD PTR [esp+eax*4+0x2c]
 8048498:       8b 44 24 1c             mov    eax,DWORD PTR [esp+0x1c]
 804849c:       89 54 84 2c             mov    DWORD PTR [esp+eax*4+0x2c],edx
 80484a0:       8b 44 24 1c             mov    eax,DWORD PTR [esp+0x1c]
 80484a4:       8d 50 01                lea    edx,[eax+0x1]
 80484a7:       8b 44 24 28             mov    eax,DWORD PTR [esp+0x28]
 80484ab:       89 44 94 2c             mov    DWORD PTR [esp+edx*4+0x2c],eax
 80484af:       83 44 24 1c 01          add    DWORD PTR [esp+0x1c],0x1
 80484b4:       8b 44 24 18             mov    eax,DWORD PTR [esp+0x18]
 80484b8:       8b 54 24 24             mov    edx,DWORD PTR [esp+0x24]
 80484bc:       29 c2                   sub    edx,eax
 80484be:       89 d0                   mov    eax,edx
 80484c0:       83 e8 01                sub    eax,0x1
 80484c3:       3b 44 24 1c             cmp    eax,DWORD PTR [esp+0x1c]
 80484c7:       7f a1                   jg     804846a
 80484c9:       83 44 24 18 01          add    DWORD PTR [esp+0x18],0x1
 80484ce:       8b 44 24 24             mov    eax,DWORD PTR [esp+0x24]
 80484d2:       83 e8 01                sub    eax,0x1
 80484d5:       3b 44 24 18             cmp    eax,DWORD PTR [esp+0x18]
 80484d9:       7f 85                   jg     8048460
 80484db:       c7 44 24 20 00 00 00    mov    DWORD PTR [esp+0x20],0x0
 80484e2:       00 
 80484e3:       eb 1d                   jmp    8048502
 80484e5:       8b 44 24 20             mov    eax,DWORD PTR [esp+0x20]
 80484e9:       8b 44 84 2c             mov    eax,DWORD PTR [esp+eax*4+0x2c]
 80484ed:       89 44 24 04             mov    DWORD PTR [esp+0x4],eax
 80484f1:       c7 04 24 b0 85 04 08    mov    DWORD PTR [esp],0x80485b0
 80484f8:       e8 f3 fd ff ff          call   80482f0
 80484fd:       83 44 24 20 01          add    DWORD PTR [esp+0x20],0x1
 8048502:       8b 44 24 20             mov    eax,DWORD PTR [esp+0x20]
 8048506:       3b 44 24 24             cmp    eax,DWORD PTR [esp+0x24]
 804850a:       7c d9                   jl     80484e5
 804850c:       b8 00 00 00 00          mov    eax,0x0
 8048511:       c9                      leave
 8048512:       c3                      ret
 8048513:       66 90                   xchg   ax,ax
 8048515:       66 90                   xchg   ax,ax
 8048517:       66 90                   xchg   ax,ax
 8048519:       66 90                   xchg   ax,ax
 804851b:       66 90                   xchg   ax,ax
 804851d:       66 90                   xchg   ax,ax
 804851f:       90                      nop

08048520 <__libc_csu_init>:
 8048520:       55                      push   ebp
 8048521:       57                      push   edi
 8048522:       31 ff                   xor    edi,edi
 8048524:       56                      push   esi
 8048525:       53                      push   ebx
 8048526:       e8 25 fe ff ff          call   8048350
 804852b:       81 c3 d5 1a 00 00       add    ebx,0x1ad5
 8048531:       83 ec 1c                sub    esp,0x1c
 8048534:       8b 6c 24 30             mov    ebp,DWORD PTR [esp+0x30]
 8048538:       8d b3 0c ff ff ff       lea    esi,[ebx-0xf4]
 804853e:       e8 71 fd ff ff          call   80482b4
 8048543:       8d 83 08 ff ff ff       lea    eax,[ebx-0xf8]
 8048549:       29 c6                   sub    esi,eax
 804854b:       c1 fe 02                sar    esi,0x2
 804854e:       85 f6                   test   esi,esi
 8048550:       74 27                   je     8048579
 8048552:       8d b6 00 00 00 00       lea    esi,[esi]
 8048558:       8b 44 24 38             mov    eax,DWORD PTR [esp+0x38]
 804855c:       89 2c 24                mov    DWORD PTR [esp],ebp
 804855f:       89 44 24 08             mov    DWORD PTR [esp+0x8],eax
 8048563:       8b 44 24 34             mov    eax,DWORD PTR [esp+0x34]
 8048567:       89 44 24 04             mov    DWORD PTR [esp+0x4],eax
 804856b:       ff 94 bb 08 ff ff ff    call   DWORD PTR [ebx+edi*4-0xf8]
 8048572:       83 c7 01                add    edi,0x1
 8048575:       39 f7                   cmp    edi,esi
 8048577:       75 df                   jne    8048558
 8048579:       83 c4 1c                add    esp,0x1c
 804857c:       5b                      pop    ebx
 804857d:       5e                      pop    esi
 804857e:       5f                      pop    edi
 804857f:       5d                      pop    ebp
 8048580:       c3                      ret
 8048581:       eb 0d                   jmp    8048590
 8048583:       90                      nop
 8048584:       90                      nop
 8048585:       90                      nop
 8048586:       90                      nop
 8048587:       90                      nop
 8048588:       90                      nop
 8048589:       90                      nop
 804858a:       90                      nop
 804858b:       90                      nop
 804858c:       90                      nop
 804858d:       90                      nop
 804858e:       90                      nop
 804858f:       90                      nop

08048590 <__libc_csu_fini>:
 8048590:       f3 c3                   repz   ret

Disassembly of section .fini:

08048594 <_fini>:
 8048594:       53                      push   ebx
 8048595:       83 ec 08                sub    esp,0x8
 8048598:       e8 b3 fd ff ff          call   8048350
 804859d:       81 c3 63 1a 00 00       add    ebx,0x1a63
 80485a3:       83 c4 08                add    esp,0x8
 80485a6:       5b                      pop    ebx
 80485a7:       c3                      ret
```
  

# What I learned in the lab 
Cross-compiling is a process where the source code is compiled on a host system that is different from the target system that will run the compiled code.
1. **cross-compiling-x86**: This involves compiling code that is intended to run on an x86 architecture. The output provided is a disassembly of an x86 executable, which means the compiler produced machine code intended for x86 processors.
2. **cross-compiling-arm**: In this case, the compilation is targeted for ARM architecture. The output is an ARM disassembly, indicating the compiled machine code is for ARM-based processors, which are common in mobile devices and increasingly in other computing platforms.
3. **x86-x86**: This seems a bit redundant as it suggests the code is compiled for the same architecture as the host. This is not cross-compiling but native compiling. The disassembly output would be the same as the host architecture, which is x86 in this case.
