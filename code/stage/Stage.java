package risk;

import java.util.*;
import java.io.*;

interface Stage {

    public abstract void play(List<Territory> clickedTerritories);

    public abstract List<Territory> setClickableTerritories();

    public abstract boolean checkEndStage();

}
