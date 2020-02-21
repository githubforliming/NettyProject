package com.freeedu.test;

import javax.sound.midi.Soundbank;
import java.nio.channels.SocketChannel;


public class Tes {
    private Entity entity=null;

    public Tes(){
       entity.testStatic();
    }

    public Entity getEntity() {
        return entity;
    }

    public static void main(String[] args) {
        System.out.println(new Tes().getEntity());
    }

}
