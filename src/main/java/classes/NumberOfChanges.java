package classes;

import java.util.Objects;

public class NumberOfChanges {

    private int codeChurnValue;

    public int getCodeChurnValue() {
        return codeChurnValue;
    }

    public void setCodeChurnValue(int codeChurnValue) {
        this.codeChurnValue = codeChurnValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfChanges that = (NumberOfChanges) o;
        return codeChurnValue == that.codeChurnValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeChurnValue);
    }
}
