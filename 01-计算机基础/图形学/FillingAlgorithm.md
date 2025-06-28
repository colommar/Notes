
# Filling algorithm

## Scan-line algorithm VS Seed-filling algorithm
**Seed Fill Algorithm** and **Scanline Fill Algorithm** are two common graphic filling algorithms, and they differ in terms of implementation, applicable scenarios, efficiency, and more. Below, we will explain these differences in detail:

### 1. **Basic Concept:**

- **Seed Fill Algorithm:**
    
    - Seed filling starts from a seed point (starting point) and gradually expands to fill the color until the entire enclosed area is filled. It checks the neighboring pixels (in four or eight directions) and fills the pixels that meet the conditions, continuing until there are no more pixels to fill.
    - It is a **recursive algorithm** or a **non-recursive algorithm** implemented with a stack, suitable for filling irregular areas.
- **Scanline Fill Algorithm:**
    
    - The scanline fill algorithm works by scanning each horizontal scanline of the image. For each scanline, it finds the left and right boundaries of the intersection points, and then fills the area between these two points.
    - It fills based on **edge information**, filling the area between intersection points on each scanline, and is suitable for filling polygons or enclosed regions.

### 2. **Implementation:**

- **Seed Fill Algorithm:**
    
    - The algorithm is implemented through **recursion** or **stacks**. It starts from a seed pixel, checks the pixels in four or eight directions, and if these pixels meet the fill conditions (e.g., their color is not the boundary color), it continues recursively or adds them to the stack for further filling.
    - It is typically used for **smaller, irregularly shaped areas** and provides higher flexibility.
- **Scanline Fill Algorithm:**
    
    - The scanline fill algorithm requires calculating the intersection points of a polygon on each scanline. Once the intersection points are found, the area between these points is filled along the scanline.
    - It requires **sorting** the intersection points by their x-coordinates to ensure that the filled area is from left to right. This algorithm is particularly well-suited for filling **polygons** or regular enclosed regions.

### 3. **Applicable Scenarios:**

- **Seed Fill Algorithm:**
    
    - Suitable for **irregularly shaped areas**, such as a region in an image surrounded by an irregular boundary. Commonly used for **filling closed regions**, like coloring or image area filling.
    - Due to its recursive nature, it adapts well to irregular boundaries.
- **Scanline Fill Algorithm:**
    
    - The scanline fill algorithm is typically used for **closed polygons**, especially when the boundaries of the polygon are known (e.g., in graphic editors for filling graphic regions).
    - It is widely used in **computer graphics for polygon filling**, particularly when rendering regular shapes like triangles, rectangles, and trapezoids, with higher efficiency.

### 4. **Algorithm Complexity:**

- **Seed Fill Algorithm:**
    
    - Due to the use of recursion or stacks, the time complexity of the seed fill algorithm is generally higher. The worst-case time complexity is **O(n)**, where n is the number of pixels in the filled area.
    - For large regions with many pixels to fill, the deep recursion may lead to stack overflow problems.
- **Scanline Fill Algorithm:**
    
    - The time complexity of the scanline algorithm is usually lower, typically **O(n log n)**, where n is the number of intersection points. This is because, for each scanline, the intersection points need to be sorted and filled.
    - It is more efficient than the seed fill algorithm when handling most regular shapes (like rectangles, trapezoids, etc.).

### 5. **Advantages and Disadvantages Comparison:**

|Feature|**Seed Fill Algorithm**|**Scanline Fill Algorithm**|
|---|---|---|
|**Applicable Shapes**|Irregular areas, any closed region|Polygons (especially regular polygons)|
|**Filling Method**|Pixel-by-pixel filling, recursion or stack-based|Line-by-line filling, fills between boundary intersections|
|**Filling Efficiency**|Faster for smaller areas, but deep recursion can slow down for large areas|More efficient, especially for regular shapes|
|**Implementation Difficulty**|Simple, easy to understand|Requires sorting intersections, more complex but suits most polygons|
|**Storage Requirements**|Needs recursion or stack to store current pixels|Requires storage of intersection points and sorting|
|**Advantages**|Flexible, suitable for irregular area filling|Efficient, ideal for filling closed polygon areas|
|**Disadvantages**|Slower, may cause stack overflow for deep recursion|Suitable for regular areas, may not work well with complex regions|

### 6. **Summary:**

- **Seed Fill Algorithm** is suitable for filling tasks involving irregular or free-form shapes. It primarily relies on recursion or a stack to progressively fill the area, making it highly flexible for handling complex boundaries.
- **Scanline Fill Algorithm** is ideal for regular polygons, especially closed polygons. It can efficiently fill areas by calculating intersections along scanlines and is commonly used in rendering and computer graphics.

For most graphic applications, if the area to be filled is a regular polygon, **Scanline Fill Algorithm** will be more efficient. However, for irregular or complex regions, the **Seed Fill Algorithm** provides better flexibility.

## Code and Display

Let's break down and explain this code in detail, step by step.

### 1. **Including Required Libraries:**

```cpp
#include <graphics.h>
#include <vector>
#include <algorithm>
#include <conio.h>
#include <queue>
using namespace std;
```

- **`graphics.h`**: This is the header for the graphics library that provides functions for creating and manipulating graphics in a window. It is commonly used in Turbo C++ and similar environments for simple graphics programming.
- **`vector`**: This standard library provides dynamic arrays (vectors) for storing data. It is used here for storing the points that define the shapes (trapezoid and rectangle).
- **`algorithm`**: While included, it doesn't seem to be directly used in the code provided.
- **`conio.h`**: This header provides functions for console I/O operations, such as `_getch()` used later to pause the program before closing the graphics window.
- **`queue`**: This header provides the `queue` container, which is used for the BFS (Breadth-First Search) algorithm to fill the shapes.

### 2. **Direction Arrays for Neighbor Checking:**

```cpp
int dx[] = { -1, 1, 0, 0 }; 
int dy[] = { 0, 0, -1, 1 };
```

- **`dx[]` and `dy[]`**: These two arrays are used to define the four possible directions to move when checking neighboring pixels during BFS:
    - `dx[]`: Moves left (`-1`), right (`1`), and keeps x-axis fixed (`0`).
    - `dy[]`: Moves up (`-1`), down (`1`), and keeps y-axis fixed (`0`).

### 3. **Scanline Fill Function:**

```cpp
void scanlineFill(int x, int y, COLORREF fillColor) {
    queue<pair<int, int>> bfsQueue;
    bfsQueue.push({x, y});

    COLORREF boundaryColor = RGB(255, 255, 255);

    if (getpixel(x, y) == fillColor) return;

    bool visited[800][600] = {false};
    visited[x][y] = true;

    while (!bfsQueue.empty()) {
        pair<int, int> current = bfsQueue.front();
        bfsQueue.pop();

        int cx = current.first, cy = current.second;

        putpixel(cx, cy, fillColor);

        for (int i = 0; i < 4; i++) {
            int nx = cx + dx[i];
            int ny = cy + dy[i];

            if (nx >= 0 && ny >= 0 && nx < 800 && ny < 600 && !visited[nx][ny]) {
                if (getpixel(nx, ny) != boundaryColor && getpixel(nx, ny) != fillColor) {
                    bfsQueue.push({nx, ny});
                    visited[nx][ny] = true;  
                }
            }
        }
    }
}
```

**Explanation**:

- **Function Purpose**: This function implements a **BFS (Breadth-First Search)** to flood-fill an area with a given color (`fillColor`), starting from the pixel at coordinates `(x, y)`.
    
- **`bfsQueue`**: A queue used to keep track of the pixels that need to be filled. BFS explores pixels level by level (in all 4 directions), starting from the initial point.
    
- **Boundary Color**: The code assumes that the boundary color is white (`RGB(255, 255, 255)`), meaning it will stop filling once it reaches a white pixel (boundary).
    
- **Check if Already Filled**: If the starting pixel is already filled with `fillColor`, the function exits immediately to avoid unnecessary work.
    
- **Visited Array**: An array of boolean values (`visited[800][600]`) is used to keep track of which pixels have already been processed, preventing redundant work.
    
- **BFS Loop**: The algorithm processes each pixel by checking its neighbors (in 4 directions). If a neighbor has not been visited and isn't part of the boundary (white) or already filled, it is added to the queue for further processing.
    
- **`putpixel`**: This function sets the color of the current pixel to `fillColor`.
    
- **Neighbor Check**: For each direction, the algorithm checks if the new pixel position (`nx`, `ny`) is within the valid image bounds and if it should be filled. If so, the pixel is added to the queue and marked as visited.
    

### 4. **Main Function:**

```cpp
int main() {
    initgraph(800, 600); 

    setbkcolor(BLACK);
    cleardevice();
    int x0 = 400, y0 = 300; 
    int h = 50, b = 100;  
    
    vector<POINT> trapezoid = {
        {x0 - h, y0 - h}, {x0 - h - b, y0}, {x0 + h + b, y0}, {x0 + h, y0 - h}
    };

    vector<POINT> rectangle = {
        {x0 - 2 * h - b, y0}, {x0 - 2 * h - b, y0 + h}, {x0 + 2 * h + b, y0 + h}, {x0 + 2 * h + b, y0}
    };
```

**Explanation**:

- **Graphics Initialization**: `initgraph(800, 600)` initializes a graphics window of size 800x600 pixels.
    
- **Background Color**: `setbkcolor(BLACK)` sets the background color of the window to black, and `cleardevice()` clears the screen to apply the background color.
    
- **Trapezoid and Rectangle Definitions**:
    
    - `x0`, `y0` are the coordinates for the center of the shapes.
    - `h` and `b` define the height and the base of the trapezoid.
    - Two shapes are defined here: a trapezoid and a rectangle, using `vector<POINT>` to store their vertices. Each shape's vertices are defined relative to `x0` and `y0`.

### 5. **Drawing the Shapes:**

```cpp
    for (size_t i = 0; i < trapezoid.size(); i++) {
        POINT p1 = trapezoid[i];
        POINT p2 = trapezoid[(i + 1) % trapezoid.size()];
        line(p1.x, p1.y, p2.x, p2.y);
    }
    for (size_t i = 0; i < rectangle.size(); i++) {
        POINT p1 = rectangle[i];
        POINT p2 = rectangle[(i + 1) % rectangle.size()];
        line(p1.x, p1.y, p2.x, p2.y);
    }
```

- **Drawing Lines**: For each shape, the program draws lines connecting the points in the `trapezoid` and `rectangle` vectors using the `line` function. This forms the outline of the trapezoid and rectangle shapes.

### 6. **Drawing Ellipses:**

```cpp
    int left1 = x0 - h - b + 20 - 30;  
    int top1 = y0 + h - 30;            
    int right1 = x0 - h - b + 20 + 30; 
    int bottom1 = y0 + h + 30;         

    int left2 = x0 + h + b - 20 - 30;  
    int top2 = y0 + h - 30;            
    int right2 = x0 + h + b - 20 + 30; 
    int bottom2 = y0 + h + 30;         

    setfillcolor(RGB(50, 50, 50)); 
    fillellipse(left1, top1, right1, bottom1); 
    fillellipse(left2, top2, right2, bottom2); 
```

- **Ellipses Coordinates**: Two sets of coordinates (`left1`, `top1`, `right1`, `bottom1`) and (`left2`, `top2`, `right2`, `bottom2`) define the bounding boxes for the ellipses.
    
- **`setfillcolor`**: This function sets the fill color for the ellipses. The color is set to a dark gray (`RGB(50, 50, 50)`).
    
- **`fillellipse`**: This function draws and fills two ellipses using the previously defined bounding boxes.
    

### 7. **Flood Filling with BFS:**

```cpp
    scanlineFill(400, 275, RGB(255, 0, 0));  
    scanlineFill(400, 325, RGB(255, 0, 0));  
```

- **Calling BFS Fill**: The `scanlineFill` function is called twice to fill two regions with the red color (`RGB(255, 0, 0)`):
    - One starting from `(400, 275)` for the trapezoid.
    - One starting from `(400, 325)` for the rectangle.

### 8. **End of Program:**

```cpp
    _getch();
    closegraph();
    return 0;
}
```

- **`_getch()`**: This function pauses the program, waiting for a keypress. This keeps the window open until the user presses a key.
    
- **`closegraph()`**: This function closes the graphics window.
    
```cpp
#include <graphics.h>
#include <vector>
#include <queue>
#include <conio.h>
using namespace std;

// Direction arrays for BFS traversal
int dx[] = {-1, 1, 0, 0};
int dy[] = {0, 0, -1, 1};

// BFS-based scanline fill function
void scanlineFill(int x, int y, COLORREF fillColor) {
    queue<pair<int, int>> bfsQueue;
    bfsQueue.push({x, y});

    COLORREF boundaryColor = RGB(255, 255, 255); // White as the boundary color

    // Exit if the starting pixel is already filled
    if (getpixel(x, y) == fillColor) return;

    bool visited[800][600] = {false}; // Visited array to avoid redundant processing
    visited[x][y] = true;

    while (!bfsQueue.empty()) {
        pair<int, int> current = bfsQueue.front();
        bfsQueue.pop();

        int cx = current.first, cy = current.second;

        // Fill the current pixel
        putpixel(cx, cy, fillColor);

        // Check neighbors in all four directions
        for (int i = 0; i < 4; i++) {
            int nx = cx + dx[i];
            int ny = cy + dy[i];

            if (nx >= 0 && ny >= 0 && nx < 800 && ny < 600 && !visited[nx][ny]) {
                if (getpixel(nx, ny) != boundaryColor && getpixel(nx, ny) != fillColor) {
                    bfsQueue.push({nx, ny});
                    visited[nx][ny] = true;
                }
            }
        }
    }
}

int main() {
    initgraph(800, 600); // Initialize the graphics window
    setbkcolor(BLACK);
    cleardevice();

    int x0 = 400, y0 = 300; // Center of the shapes
    int h = 50, b = 100;    // Trapezoid height and base length

    // Define trapezoid vertices
    vector<POINT> trapezoid = {
        {x0 - h, y0 - h}, {x0 - h - b, y0}, {x0 + h + b, y0}, {x0 + h, y0 - h}
    };

    // Define rectangle vertices
    vector<POINT> rectangle = {
        {x0 - 2 * h - b, y0}, {x0 - 2 * h - b, y0 + h}, {x0 + 2 * h + b, y0 + h}, {x0 + 2 * h + b, y0}
    };

    // Draw trapezoid
    for (size_t i = 0; i < trapezoid.size(); i++) {
        POINT p1 = trapezoid[i];
        POINT p2 = trapezoid[(i + 1) % trapezoid.size()];
        line(p1.x, p1.y, p2.x, p2.y);
    }

    // Draw rectangle
    for (size_t i = 0; i < rectangle.size(); i++) {
        POINT p1 = rectangle[i];
        POINT p2 = rectangle[(i + 1) % rectangle.size()];
        line(p1.x, p1.y, p2.x, p2.y);
    }

    // Draw and fill ellipses
    int left1 = x0 - h - b + 20 - 30;
    int top1 = y0 + h - 30;
    int right1 = x0 - h - b + 20 + 30;
    int bottom1 = y0 + h + 30;

    int left2 = x0 + h + b - 20 - 30;
    int top2 = y0 + h - 30;
    int right2 = x0 + h + b - 20 + 30;
    int bottom2 = y0 + h + 30;

    setfillcolor(RGB(50, 50, 50)); // Set gray color for ellipses
    fillellipse(left1, top1, right1, bottom1);
    fillellipse(left2, top2, right2, bottom2);

    // Fill trapezoid and rectangle with red color using BFS
    scanlineFill(400, 275, RGB(255, 0, 0));
    scanlineFill(400, 325, RGB(255, 0, 0));

    // Wait for user input before closing the graphics window
    _getch();
    closegraph();
    return 0;
}

```
### Summary:

- The program creates a graphics window, draws a trapezoid and rectangle, then fills those shapes with red using a BFS-based scanline fill algorithm.

![[resouces/Pasted image 20241212205309.png]]

					fig1 colored car



## bfs
In this code, the **Breadth-First Search (BFS)** algorithm is used to fill a closed region with a specified color. The key concept of BFS is to explore the neighboring nodes (pixels, in this case) level by level, rather than going deep into one path like Depth-First Search (DFS). This method works well for filling areas in graphical applications, such as flood fill algorithms, where you want to fill all the connected pixels of a certain type (boundary).

Here’s the step-by-step explanation of the BFS approach used in the code:

### 1. **Setup and Initialization**

- **Direction Arrays (`dx[]` and `dy[]`)**: These arrays define the movement directions for BFS. The four possible directions are:
    - Left: `dx = -1, dy = 0`
    - Right: `dx = 1, dy = 0`
    - Up: `dx = 0, dy = -1`
    - Down: `dx = 0, dy = 1`

These directions will be used to explore neighboring pixels in the 2D grid (the image).

- **Queue (`bfsQueue`)**: BFS uses a queue to manage which pixel to process next. We push the starting point (x, y) into the queue initially.
    
- **Boundary Color**: The boundary color is assumed to be white (`RGB(255, 255, 255)`) because the program is supposed to fill an area that is surrounded by white pixels.
    
- **Visited Array**: This 2D array keeps track of which pixels have already been visited during the BFS. This prevents revisiting pixels, which can lead to infinite loops or redundant operations.
    

### 2. **Main BFS Logic (Flood Fill)**

- **Starting Point**: The flood fill starts from the given `(x, y)` coordinates. The first check is to see if the starting pixel is already filled with the target color (`fillColor`). If so, the function returns early to avoid unnecessary processing.
    
- **Processing the Queue**: The main BFS loop runs until the queue is empty. In each iteration:
    
    - The pixel at the front of the queue is popped and processed.
    - The pixel is colored using the `putpixel()` function to change its color to the fill color.
- **Neighbor Checking and Queue Expansion**: For each processed pixel, the algorithm looks at its 4 neighboring pixels (up, down, left, right). It checks whether the neighboring pixel is:
    
    1. Within the valid image boundaries (`0 <= nx < 800` and `0 <= ny < 600`).
    2. Not visited already.
    3. Not a boundary color (`RGB(255, 255, 255)`) or the target fill color (`fillColor`), since we want to fill the interior and avoid overwriting filled areas or crossing the boundary.
- **Push to Queue**: If a neighbor meets the criteria, it is added to the queue and marked as visited. This ensures that BFS will expand the filled region to include all connected interior pixels.
    

### 3. **Termination**

- The BFS loop continues until all reachable and fillable pixels are processed. Once the queue is empty, the entire connected region has been filled.

### 4. **Practical Application (Flood Fill)**

- The flood fill algorithm is used to fill two different shapes (a trapezoid and a rectangle) in the program. The `scanlineFill()` function is called with the coordinates of a point within each shape, and the region surrounding that point is filled with red (`RGB(255, 0, 0)`).

### 5. **Summary of BFS in This Context**

- BFS is applied to explore all the connected interior pixels, starting from a given point.
- The algorithm systematically fills the region by checking its 4 neighbors and uses a queue to ensure each pixel is processed only once.
- BFS guarantees that every connected pixel will be filled in a level-wise manner, starting from the initial point and expanding outwards.

This method is ideal for 2D flood fill operations, where the aim is to fill an enclosed region without crossing the boundary, using the BFS technique to efficiently explore all reachable pixels.
 