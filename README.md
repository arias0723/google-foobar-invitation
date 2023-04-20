# Google Foobar Invitation

Some (interesting) problems of the invitation I received from Google selection program Foobar.


# Problem 1: Bringing a Gun to a Trainer Fight

Write a function solution(dimensions, your_position, trainer_position, distance) that gives an array of 2 integers of the width and height of the room, an array of 2 integers of your x and y coordinates in the room, an array of 2 integers of the trainer's x and y coordinates in the room, and returns an integer of the number of distinct directions that you can fire to hit the elite trainer, given the maximum distance that the beam can travel.

The room has integer dimensions [1 < x_dim <= 1250, 1 < y_dim <= 1250]. You and the elite trainer are both positioned on the integer lattice at different distinct positions (x, y) inside the room such that [0 < x < x_dim, 0 < y < y_dim]. Finally, the maximum distance that the beam can travel before becoming harmless will be given as an integer 1 < distance <= 10000.

-- Test cases --

Input: ([3,2], [1,1], [2,1], 4)
Output: 7

Input: ([300,275], [150,150], [185,100], 500)
Output: 9

# Solution:
