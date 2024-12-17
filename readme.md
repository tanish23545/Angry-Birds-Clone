# Angry Birds Clone - Detailed README

## Overview

This is an Angry Birds-style physics-based game built using **LibGDX**, a Java framework for game development. The game consists of launching various objects (birds) to destroy structures made of blocks, with the goal of knocking over pigs or other targets. The game features realistic physics interactions, including gravity, collision detection, and damage calculations.

## Features

- **Physics-Based Gameplay**: Uses Box2D physics engine for real-world interactions such as gravity, friction, and restitution.
- **Multiple Bird Types**: Various bird types with unique abilities for different levels and strategies.
- **Level Design**: Multiple levels with increasing difficulty, featuring structures made from destructible materials.
- **Health and Damage**: Materials and structures take damage when hit by birds or other objects.
- **Visual Effects**: Realistic physics effects such as block destruction, bouncing, and falling debris.

## Game Mechanics

### 1. **Bird Launching**
- Players can drag and release the bird to launch it towards the target structure.
- Each bird has a specific speed, trajectory, and damage multiplier.
- Some birds may have unique abilities, such as splitting into multiple smaller birds or activating explosive powers on impact.

### 2. **Target Structures**
- The target structures are made up of different types of blocks (wood, stone, metal, etc.) stacked together.
- Each block type has its own durability and damage threshold. 
  - Wood blocks are easy to destroy.
  - Stone blocks are more durable and harder to break.
  - Metal blocks are the toughest and require a significant amount of force to damage.

### 3. **Pigs and Damage**
- The pigs (or other targets) are placed within the structures.
- If a pig is hit directly by a bird or debris from the destroyed blocks, it will take damage.
- Once a pig's health reaches zero, it is destroyed.

### 4. **Physics Engine**
- **Box2D** is used to simulate realistic physics. Objects in the game (birds, blocks, etc.) react to gravity, collisions, and forces.
  - **Gravity**: All objects are affected by gravity, which pulls them down to the ground.
  - **Friction**: Blocks in contact with the ground have friction to prevent them from sliding too easily.
  - **Restitution**: Objects can bounce off each other based on their restitution value.

### 5. **Materials and Damage**
- Each material type (wood, stone, metal) has different properties for how much force it can withstand before breaking.
- When a bird hits a block, the block takes damage based on the force of the impact and the material's strength.
- Birds and blocks can take damage from impacts, and health is tracked for each object.

## Installation

### Prerequisites
To run the game, you need the following tools:
- **Java Development Kit (JDK)** version 8 or higher
- **LibGDX** game development framework
- **Box2D** physics engine (included in LibGDX)
- **Gradle** (for building the project)

### Step-by-Step Installation

1. **Clone the Repository**

   Clone the repository to your local machine:

   ```bash
   git clone https://github.com/yourusername/angry-birds-clone.git
   cd angry-birds-clone
