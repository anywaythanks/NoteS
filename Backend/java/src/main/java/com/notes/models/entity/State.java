package com.notes.models.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum State {
   PENDING_CREATE(0),
   ACTIVE(1),
   PENDING_MODIFY(2),
   ACTIVE_MODIFIED(3),
   FAILED(4),
   PENDING_ARCHIVE(5),
   ARCHIVED(6);
   final int id;
   private static final Set<Transition> transitions = init();
   final static Map<Integer, State> map = Arrays.stream(State.values())
           .collect(Collectors.toMap(State::getId, Function.identity()));

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
              .then(State.PENDING_CREATE, State.ACTIVE)
              .then(State.PENDING_CREATE, State.FAILED)
              .then(State.PENDING_MODIFY, State.ACTIVE_MODIFIED)
              .then(State.PENDING_MODIFY, State.FAILED)
              .then(State.PENDING_ARCHIVE, State.ARCHIVED)
              .then(State.PENDING_ARCHIVE, State.FAILED)
              .then(State.ACTIVE, State.PENDING_MODIFY)
              .then(State.ACTIVE, State.PENDING_ARCHIVE)
              .then(State.ACTIVE_MODIFIED, State.PENDING_MODIFY)
              .then(State.ACTIVE_MODIFIED, State.PENDING_ARCHIVE)
              .then(State.FAILED, State.PENDING_MODIFY)
              .then(State.FAILED, State.PENDING_ARCHIVE)
              .then(State.PENDING_CREATE, State.PENDING_MODIFY)
              .then(State.PENDING_CREATE, State.PENDING_ARCHIVE)
              .then(State.PENDING_MODIFY, State.PENDING_ARCHIVE)
              .build();
   }

   public static State valueOf(int i) {
      return map.get(i);
   }

   public boolean isValidTransition(State newState) {
      return transitions.contains(new Transition(this, newState));
   }
}
