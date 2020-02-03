# WireWorld

Wire World cellular automaton, written using JavaFX.

## The Game

Wire World is a simple cellular automaton designed to simulate transistors. For a more detailed description of how it works, see [the wiki page](https://en.wikipedia.org/wiki/Wireworld).

## How to Play

### Mouse Controls

The board will appear empty to begin with. Left click and drag to place conductor tiles. Right click to place an electron.

### Keyboard Controls

Pressing the space bar will pause the simulation. The backspace or delete keys will clear the board. Up and down arrows will alter the speed at which the simulation runs.

Simulation states can be saved by holding shift and pressing a number key, indicating the ID of the save. States can be loaded by pressing the number key corresponding to the ID of the save. Since there are only ten number keys, only ten simulations can be saved.

## Save on Close

The simulation is saved when the window is closed and loaded once the window is opened. This means that simulations will appear to resume from the exact point at which they were when they were closed.
