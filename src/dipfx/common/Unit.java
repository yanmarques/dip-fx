package dipfx.common;

import java.util.function.Consumer;

public interface Unit<T> {
    T getValue();
    void setValue(T value);
    void setOnValueChanged(Consumer<Unit<T>> unitConsumer);
}
