package org.fuzzy.model;

import java.math.BigInteger;
import java.util.Objects;

public class ParsObject {

    private String group;
    private String type;
    private Long number;
    private BigInteger weight;

    public ParsObject() {
    }

    public ParsObject(String group, String type, Long number, BigInteger weight) {
        this.group = group;
        this.type = type;
        this.number = number;
        this.weight = weight;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public BigInteger getWeight() {
        return weight;
    }

    public void setWeight(BigInteger weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsObject object = (ParsObject) o;
        return Objects.equals(group, object.group) && Objects.equals(type, object.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, type);
    }

    @Override
    public String toString() {
        return "ParsObject{" +
                "group='" + group + '\'' +
                ", type='" + type + '\'' +
                ", number=" + number +
                ", weight=" + weight +
                '}';
    }
}
