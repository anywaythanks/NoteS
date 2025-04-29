package com.notes.models.entity;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum State {
   PENDING(0),
   ACTIVE(1),
   FAILED(2),
   PENDING_ARCHIVE(3),
   ARCHIVED(4);
   final int id;
   private static final Set<Transition> transitions = init();

   private record Transition(State from, State to) {

   }

   State(int id) {
      this.id = id;
   }

   private record SetBuilder(Set<Transition> set) {
      static SetBuilder of() {
         return new SetBuilder(new HashSet<>());
      }

      SetBuilder then(State oldState, State newState) {
         set.add(new Transition(oldState, newState));
         return this;
      }

      Set<Transition> build() {
         return set;
      }
   }

   static Set<Transition> init() {
      return SetBuilder.of()
              .then(State.PENDING, State.ACTIVE)
              .then(State.PENDING, State.FAILED)
              .then(State.PENDING_ARCHIVE, State.ARCHIVED)
              .then(State.PENDING_ARCHIVE, State.FAILED)
              .then(State.ACTIVE, State.PENDING)
              .then(State.ACTIVE, State.PENDING_ARCHIVE)
              .then(State.FAILED, State.PENDING)
              .then(State.FAILED, State.PENDING_ARCHIVE)
              .build();
   }

   public static State valueOf(int i) {
      return switch(i) {
         case 0 -> PENDING;
         case 1 -> ACTIVE;
         case 2 -> FAILED;
         case 3 -> ARCHIVED;
         default -> throw new IllegalArgumentException("Unknown note states: " + i);
      };
   }

   public boolean isValidTransition(State newState) {
      return transitions.contains(new Transition(this, newState));
   }
}
