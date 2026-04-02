package com.narxoz.rpg;

import com.narxoz.rpg.arena.*;
import com.narxoz.rpg.chain.*;
import com.narxoz.rpg.command.*;
import com.narxoz.rpg.tournament.TournamentEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 6 Demo ===\n");

        ArenaFighter hero = new ArenaFighter("Hero", 100, 0.2, 25, 5, 18, 3);
        ArenaOpponent opponent = new ArenaOpponent("Champion", 90, 14);

        ActionQueue queue = new ActionQueue();
        queue.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
        queue.enqueue(new HealCommand(hero, 20));
        queue.enqueue(new DefendCommand(hero, 0.15));

        System.out.println("Queue:");
        for (String s : queue.getCommandDescriptions()) {
            System.out.println(s);
        }

        queue.undoLast();

        System.out.println("\nAfter undo:");
        for (String s : queue.getCommandDescriptions()) {
            System.out.println(s);
        }

        queue.executeAll();

        DefenseHandler dodge = new DodgeHandler(0.5, 42);
        DefenseHandler block = new BlockHandler(0.3);
        DefenseHandler armor = new ArmorHandler(5);
        DefenseHandler hp = new HpHandler();

        dodge.setNext(block).setNext(armor).setNext(hp);

        System.out.println("\nHP before: " + hero.getHealth());
        dodge.handle(20, hero);
        System.out.println("HP after: " + hero.getHealth());

        TournamentResult result = new TournamentEngine(hero, opponent)
                .setRandomSeed(42)
                .runTournament();

        System.out.println("\nWinner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());

        for (String line : result.getLog()) {
            System.out.println(line);
        }
    }
}