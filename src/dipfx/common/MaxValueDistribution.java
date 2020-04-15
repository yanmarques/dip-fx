package dipfx.common;

import java.util.ArrayList;

public class MaxValueDistribution {
    protected int maximum;
    protected ArrayList<Unit<Integer>> units;

    public MaxValueDistribution(int maximum, ArrayList<Unit<Integer>> units) {
        if (units.size() < 2) {
            throw new IllegalArgumentException("The minimum number of units for sane configuration, should be 2.");
        }

        this.maximum = maximum;
        this.units = units;

        units.forEach(unit -> unit.setOnValueChanged(this::check));
    }

    public void check() {
//         it is ok to pass null for now
        this.check(null);
    }

    // TODO properly handle distributing based on given unit
    public void check(Unit<Integer> unitToCheck) {
        int total = 0;
        for (Unit<Integer> unit: this.units) {
            total += unit.getValue();
        }
        if (total > this.maximum) {
            int exceeded = total - this.maximum;
            Unit<Integer> topUnit = null;
            int maxValue = Integer.MIN_VALUE;
            for (Unit<Integer> unit: this.units) {
                if (unit.getValue() > maxValue) {
                    topUnit = unit;
                    maxValue = unit.getValue();
                }
            }
            if (topUnit == null) {
                // how the hell we get here?
                throw new IllegalStateException("Failed to distribute exceeded value between units");
            } else {
                topUnit.setValue(maxValue - exceeded);
            }
        }
    }
}
