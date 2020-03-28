package dipfx.common;

import java.util.function.Consumer;

public interface Unit {
    int getValue();
    void setValue(int value);
    void setOnValueChanged(Consumer<Unit> unitConsumer);
}
