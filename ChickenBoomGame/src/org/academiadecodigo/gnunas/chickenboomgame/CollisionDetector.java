package org.academiadecodigo.gnunas.chickenboomgame;

import org.academiadecodigo.gnunas.chickenboomgame.gameobjects.Obstacle;
import org.academiadecodigo.gnunas.chickenboomgame.gameobjects.GameObject;
import org.academiadecodigo.gnunas.chickenboomgame.gameobjects.Steroid;
import org.academiadecodigo.gnunas.chickenboomgame.gameobjects.SteroidType;
import org.academiadecodigo.gnunas.chickenboomgame.players.BlackChicken;
import org.academiadecodigo.gnunas.chickenboomgame.players.Player;
import org.academiadecodigo.gnunas.chickenboomgame.players.WhiteChicken;

import java.util.LinkedList;

public class CollisionDetector {

    private LinkedList<GameObject> objects;
    private Player[] players;

    public CollisionDetector(LinkedList<GameObject> objects, Player[] players) {

        this.objects = objects;
        this.players = players;
    }


    public boolean checkRange(GameObject object, Player player) {

        if ((player.getX() >= object.getX() && player.getX() <= object.getXtoWith())
                || (player.getXtoWidth() <= object.getXtoWith() && player.getXtoWidth() >= object.getX())) {

            return (player.getY() >= object.getY() && player.getY() <= object.getYtoHeight())
                    || (player.getYtoHeight() <= object.getYtoHeight() && player.getYtoHeight() >= object.getY());
        }

        if ((player.getY() >= object.getY() && player.getY() <= object.getYtoHeight())
                || (player.getYtoHeight() <= object.getYtoHeight() && player.getYtoHeight() >= object.getY())) {

            return (player.getX() >= object.getX() && player.getX() <= object.getXtoWith())
                    || (player.getXtoWidth() <= object.getXtoWith() && player.getXtoWidth() >= object.getX());
        }

        return false;

    }

    public void checkCollision(Player player) {

        for (GameObject object : objects) {

            if (object instanceof Steroid) {

                GameObject steroids = (Steroid) object;

                if(checkRange(steroids, player)) {

                    Steroid steroid = (Steroid) object;
                    if (steroid.isUsed()){
                        continue;
                    }
                    object.delete();


                    if (steroid.getType() == SteroidType.ICE || steroid.getType() == SteroidType.SKULL) {
                        if (player instanceof WhiteChicken) {
                            BlackChicken player2 = (BlackChicken) players[1];
                            player2.setStatus(steroid.getType());
                            continue;
                        }

                        WhiteChicken player1 = (WhiteChicken) players[0];
                        player1.setStatus(steroid.getType());
                        continue;
                    }

                    if (steroid.getType() == SteroidType.CORN || steroid.getType() == SteroidType.MUSHROOM) {
                        if (player instanceof WhiteChicken) {
                            WhiteChicken player1 = (WhiteChicken) players[0];
                            player1.setStatus(steroid.getType());
                            continue;
                        }

                        BlackChicken player2 = (BlackChicken) players[1];
                        player2.setStatus(steroid.getType());
                        continue;
                    }
                }
                continue;
            }

            GameObject obstacle = (Obstacle) object;

            if (checkRange(obstacle, player)) {

                player.setCrashed();
            }


        }

    }


}
