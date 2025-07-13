# Extract: Zombie Shooter

## Overview

This project is a "3D" first-person zombie shooter game made using Java. Players must survive waves of incoming zombies to collect coins to reach the ultimate goal: extraction. This game aims to simulate a 3D environment using 2D assets only (absolutely no 3D game engine involved).

---

## How to Play

### Start Screen

- Click the **"Start Game"** button to begin. This will transition the game into the main gameplay screen.
- Game controls are shown on the bottom of the screen.

### Game

#### Top-Left Indicators

1. **Health**:
   - Represented with a heart icon.
   - Starts at 100 and decreases when a zombie reaches you.
2. **Coins**:
   - Earned by killing zombies.
3. **Wave Number**:
   - Increases as each wave is cleared. Each wave has stronger zombies (more HP, more damage).

#### Bottom-Right Ammo Display

- Shows both:
  - Current bullets in the magazine (starts with 20).
  - Remaining bullets in your inventory (starts with 100).
- A reload icon will appear when the magazine is empty.
- Press **R** to reload, only if the magazine isn't full and you have reserve ammo.
- You cannot shoot during the reloading animation.

#### Aiming and Movement

- Aim by moving your mouse.
- There is a limit to how far you can look in any direction.
- Click **Left Mouse Button** to shoot. Bullets always hit exactly where the crosshair is.

---

## Game Mechanics

1. **Waves of Zombies**:

   - Zombies spawn and move toward you in a zigzag path.
   - Each new wave introduces stronger zombies (also reward you with more coins).

2. **Zombie Damage**:

   - Zombies deal damage when they get close enough.
   - A red ring appears briefly to indicate being hit.
   - Health reaching zero ends the game.

3. **Hitbox System**:

   - The zombie hitbox approximately matches the visible body.
   - Only the closest overlapping zombie will take damage when shot.

4. **Store System**:

   - Located in the center of the map.
   - Aim at the door and press **F** to open the store.
   - It costs 20 coins per 100 bullets.

5. **Extraction Mechanic**:
   - A red car is placed on the right side of the map.
   - When you collect at least **200 coins**, aim at the car and press **F** to escape.
   - This ends the game and shows a win message.

---

## End Conditions

- **Loss**: Health reaches zero → "You Lost!" message displayed.
- **Win**: 200 coins collected and extraction triggered → "You Won!" message displayed.

---

## Notes

- Players **cannot move** around the map—only look around.
- Game simulates a 3D FPS experience using static 2D image rendering.
- Your mouse is locked to the center unless using popups (store or car).

---

## Known Bugs

- None as of now.

---

## Future Plans

1. Add sound effects for shooting and zombie actions.
2. Implement more weapons (e.g., automatic rifles).
3. Add recoil mechanics that affect the crosshair position.
