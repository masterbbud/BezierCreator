# BezierCreator

This is a project I made in order to accurately generate bezier curves manually from a given image.
Currently it takes all of its input from inside the code; select an image directory, and set x, y, and s to their ideal values (x and y should be the starting point's position, and s should be the height in bar lines of the total curve. Example: a time signature component should have s=2)
Once the program loads, click to place the first point, then from there drag out the end point and 2 bezier points to the ideal location.
(Endpoint is in red and Bezier points are in green.)
Clicking Enter will "place" the bezier curve, as well as saving its generating code in memory.
Press 'p' to print all of the current bezier code to the command line, then copy and paste into your project.
Note: Project should use 4 variables:
p = The processing object that will draw the bezier curve.
x = The x position to start drawing the curve
y = The y position to start drawing the curve
s = The size of the curve, in staff lines.

To run, run Launcher.java in a JRE.
