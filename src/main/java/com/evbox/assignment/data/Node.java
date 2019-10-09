package com.evbox.assignment.data;

import com.evbox.assignment.data.model.ChargeSession;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Node {
	private final ChargeSession value;
	private Node next;
	
}
