import os

def walk_dir(base, prefix=""):
    result = []
    for name in sorted(os.listdir(base)):
        path = os.path.join(base, name)
        if os.path.isdir(path):
            sub_items = walk_dir(path, prefix + "  ")
            if sub_items:
                result.append(f"{prefix}- **{name}**")
                result.extend(sub_items)
        elif name.endswith('.md'):
            rel = os.path.relpath(path, ".")
            rel = rel.replace("\\", "/")
            result.append(f"{prefix}- [{name}]({rel})")
    return result

if __name__ == "__main__":
    toc_lines = ["# 目录\n"] + walk_dir(".")
    try:
        with open("README.md", "r", encoding="utf-8") as f:
            content = f.read()
    except FileNotFoundError:
        content = ""
    
    if "# 目录" in content:
        toc_start = content.find("# 目录")
        toc_end = content.find("\n# ", toc_start)
        if toc_end == -1:
            toc_end = len(content)
        new_content = content[:toc_start] + "\n".join(toc_lines) + content[toc_end:]
    else:
        new_content = content + "\n\n" + "\n".join(toc_lines)
    
    # 写入文件
    with open("README.md", "w", encoding="utf-8") as f:
        f.write(new_content)
    
    print("目录已更新到 README.md")