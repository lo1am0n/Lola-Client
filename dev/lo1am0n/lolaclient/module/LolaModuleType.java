package dev.lo1am0n.lolaclient.module;

import dev.lo1am0n.lolaclient.event.LolaEvent;
import dev.lo1am0n.lolaclient.event.impl.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public enum LolaModuleType {
    Combat, // ex: 1.7 Hit Reg, Closest Hit Point
    Visual, // ex: Animations, Block Outline
    Movement, // ex: Auto Sprint
    Misc // Misc, duh...
}
