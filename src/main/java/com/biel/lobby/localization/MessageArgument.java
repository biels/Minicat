package com.biel.lobby.localization;

import java.util.Objects;
import net.kyori.adventure.text.Component;

/** Literal values with types checked against the generated message contract. */
public record MessageArgument(String name, Component value, Type type) {
    public enum Type { TEXT, NUMBER }
    public MessageArgument {
        Objects.requireNonNull(name); Objects.requireNonNull(value); Objects.requireNonNull(type);
    }
    public MessageArgument(String name, Component value) { this(name, value, Type.TEXT); }
    public static MessageArgument text(String name, String value) { return new MessageArgument(name, Component.text(value), Type.TEXT); }
    public static MessageArgument number(String name, Number value) { return new MessageArgument(name, Component.text(value.toString()), Type.NUMBER); }
}
