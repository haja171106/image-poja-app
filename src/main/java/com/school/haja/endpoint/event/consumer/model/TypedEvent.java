package com.school.haja.endpoint.event.consumer.model;

import com.school.haja.PojaGenerated;
import com.school.haja.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
