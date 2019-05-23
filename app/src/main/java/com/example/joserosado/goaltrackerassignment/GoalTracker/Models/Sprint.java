package com.example.joserosado.goaltrackerassignment.GoalTracker.Models;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Sprint {
    public List<Event> Events;

    public Sprint() {
        Events = new List<Event>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Event> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] ts) {
                return null;
            }

            @Override
            public boolean add(Event event) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Event> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends Event> collection) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Event get(int i) {
                return null;
            }

            @Override
            public Event set(int i, Event event) {
                return null;
            }

            @Override
            public void add(int i, Event event) {

            }

            @Override
            public Event remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Event> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Event> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<Event> subList(int i, int i1) {
                return null;
            }
        };
    }

}
