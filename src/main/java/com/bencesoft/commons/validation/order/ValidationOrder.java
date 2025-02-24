package com.bencesoft.commons.validation.order;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({ Default.class, First.class, Second.class, Third.class })
public interface ValidationOrder {
}
