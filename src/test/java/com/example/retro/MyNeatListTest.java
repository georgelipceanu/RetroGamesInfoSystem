package com.example.retro;

import com.example.retro.models.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyNeatListTest {

    @Test
    void add() {
        MyNeatList<Game> games = new MyNeatList<>();
        Game g1 =new Game("Fortnite","Epic","Battle Royale","Epic Games","http://www.fort.com",2017);

        games.add(g1);
        assertEquals("Fortnite",games.get(0).getTitle());
        assertEquals("Epic",games.get(0).getPublisher());
        assertEquals("Battle Royale",games.get(0).getDescription());
        assertEquals("Epic Games",games.get(0).getOgDeveloper());
        assertEquals("http://www.fort.com",games.get(0).getCoverArtURL());
        assertEquals(2017,games.get(0).getYearOfRelease());//checking if game has been added
    }

    @Test
    void set() {
        MyNeatList<Game> games = new MyNeatList<>();
        Game g1 =new Game("Fortnite","Epic","Battle Royale","Epic Games","http://www.fort.com",2017);
        games.add(g1);
        Game g2 = new Game("Mario Odyssey","Nintendo","Platformer","Nintendo","http://www.mario.com",2017);
        games.add(g2);
        Game g3 = new Game("Mario Galaxy","Ninted","Platformer","Nintendo","http://www.mario.com",2007);
        games.add(g3);

        assertEquals(0,games.indexOf(g3));
        assertEquals(1,games.indexOf(g2));
        assertEquals(2,games.indexOf(g1));


        Game g4 = new Game("Mario Galaxy","Nintendo","Platformer","Nintendo","http://www.mario.com",2007);//fixed spelling mistake in publisher
        games.set(games.indexOf(g3), g4);

        assertEquals(0,games.indexOf(g4));
        assertEquals("Nintendo", games.get(0).getPublisher());//testing change was made

    }
}