package com.example.tomaszvolanek.tamz_project_com;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LevelXmlParser {
    private static final String ns = null;


    public List<LevelDifficulty> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        }
        finally {
            in.close();
        }
    }

    private List<LevelDifficulty> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<LevelDifficulty> entries = new ArrayList<LevelDifficulty>();

        parser.require(XmlPullParser.START_TAG, ns, "leveldifficulty");
        while(parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            //starts looking for the
            if(name.equals("difficulty")) {
                entries.add(readEntry(parser));
            }
            else {
                parser.next();
            }

        }
        return entries;
    }

    private LevelDifficulty readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "difficulty");
        LevelDifficulty lvl = new LevelDifficulty();

        for (int i = 0; i < parser.getAttributeCount(); i++) {

            if (parser.getAttributeName(i).equals("enemyspawntime")) {
                lvl.setEnemySpawnTime(Long.parseLong(parser.getAttributeValue(i)));
            }
            if (parser.getAttributeName(i).equals("enemyvelocity")) {
                lvl.setEnemyVelocity(Double.parseDouble(parser.getAttributeValue(i)));
            }
            if (parser.getAttributeName(i).equals("playerfuel")) {
                lvl.setPlayerFuel(Integer.parseInt(parser.getAttributeValue(i)));
            }
            if (parser.getAttributeName(i).equals("fuelships")) {
                lvl.setFuelShips(Integer.parseInt(parser.getAttributeValue(i)));
            }
        }
        parser.next();
        return lvl;

    }
}
