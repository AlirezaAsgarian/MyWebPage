package util;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@JacksonXmlRootElement(localName = "pair")
public class Pair<K, V> implements Serializable {
    @JacksonXmlProperty(localName = "key")
    public K key;
    @JacksonXmlProperty(localName = "value")
    public V value;

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public Pair(@NamedArg("key") K var1, @NamedArg("value") V var2) {
        this.key = var1;
        this.value = var2;
    }

    public String toString() {
        return this.key + "=" + this.value;
    }

    public int hashCode() {
        int var1 = 7;
        var1 = 31 * var1 + (this.key != null ? this.key.hashCode() : 0);
        var1 = 31 * var1 + (this.value != null ? this.value.hashCode() : 0);
        return var1;
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (!(var1 instanceof Pair)) {
            return false;
        } else {
            Pair var2 = (Pair)var1;
            if (this.key != null) {
                if (!this.key.equals(var2.key)) {
                    return false;
                }
            } else if (var2.key != null) {
                return false;
            }

            if (this.value != null) {
                if (!this.value.equals(var2.value)) {
                    return false;
                }
            } else if (var2.value != null) {
                return false;
            }

            return true;
        }
    }
}

