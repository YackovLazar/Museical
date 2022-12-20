package com.ladswithlaptops.museical.classes;

import android.media.MediaMetadataRetriever;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public class Mapper {
    private static final HashSet<Song> mSongs = new HashSet<>();  // TODO Convert to HashMap
    private static final HashMap<String, Artist> mArtists = new HashMap<>();
    private static final HashSet<Album> mAlbums = new HashSet<>();  // TODO Convert to HashMap
    private static final HashMap<String, Genre> mGenres = new HashMap<>();
    private static enum itemType {SONG, ARTIST, ALBUM, GENRE}

    /**
     * Imports songs into res folder, mapping them in the process
     * @param dir Directory in which to look for songs.
     */
    public static void importSongs(String dir) {
        MediaMetadataRetriever metadata = new MediaMetadataRetriever();
        String path, title;
        Album album;
        LinkedList<Artist> currArtists = new LinkedList<>();
        LinkedList<Genre> currGenres = new LinkedList<>();
        String[] genresParsed, artistsParsed;
        Song song;

        // TODO Loop through every file, processing it if it is a music file
        while () {  // There are still files in dir
            if () continue; // If not a music file

            // TODO Move file to res and get new path
            path = "";  // New path of song after being moved
            metadata.setDataSource(path);

            // Parse Genre and Artist Metadata which could contain multiple values with deliminators
            genresParsed = parseString(metadata.extractMetadata(6).trim());
            artistsParsed = parseString(metadata.extractMetadata(2).trim());

            title = metadata.extractMetadata(7);
            // TODO Check if album exists, and if not, if is empty string, then add to albums
            album = new Album.Builder().title(metadata.extractMetadata(1)).build();
            currGenres = null;  // TODO Needs to be replaced
            currArtists = null;  // TODO Needs to be replaced

            // Create Song
            song = new Song.Builder()
                    .path(path)
                    .title(Objects.equals(title, "") ? "Unknown" : title)
                    .artists(currArtists)
                    .album(album)
                    .genres(currGenres)
                    .year((short) Integer.parseInt(metadata.extractMetadata(8)))
                    .build();
            mSongs.add(song);  // TODO Does return value need to be checked?

            // Map song and its elements
            // Map Genres TODO This needs to happen before and after song is created. AH!
            for (String gStr : genresParsed) {
                Genre currGenre = mGenres.get(gStr);
                if (currGenre != null) {
                    currGenre.addSong(song);
                    mGenres.put(gStr, currGenre);
                }
                currGenres.add(currGenre);
            }
            // Map Artists TODO This needs to happen before and after song is created. AH!
            for (String aStr : artistsParsed) {
                Artist currArtist = mArtists.get(aStr);
                if (currArtists != null) {
                    currArtist.addSong(song);
                    mArtists.put(aStr, currArtist);
                }
                currArtists.add(currArtist);
            }
        }
    }

    /**
     * Splits a string based on the first deliminator that is checked and found from the list ["//", ";"] in that order.
     * @param toParse String to be split if any deliminator exists
     * @return Array of Strings after split by the first matched deliminator,
     * an array with a single element if no deliminator is matched,
     * or an array a single element "Unknown" if an empty String.
     * @exception NullPointerException if argument is null.
     */
    private static String[] parseString(String toParse) {
        if (toParse == null)
            throw new NullPointerException();
        String[] parsed;
        if (!toParse.equals("")) {
            String[] deliminators = {"//", ";"};  // TODO Review deliminator options
            if (toParse.contains(deliminators[0])) {
                parsed = toParse.split(deliminators[0]);
            } else if (toParse.contains(deliminators[1])) {
                parsed = toParse.split(deliminators[1]);
            } else {
                parsed = new String[]{toParse};
            }
        } else {  // If empty string
            parsed = new String[]{"Unknown"};
        }
        return parsed;
    }

    private boolean itemExists(Object item) {
        if (item instanceof Song) {
            return mSongs.contains(item);
        } else if (item instanceof Artist) {
            return mArtists.containsKey(((Artist) item).getName());
        } else if (item instanceof Album) {
            return mAlbums.contains(item);
        } else if (item instanceof Genre) {
            return mGenres.containsKey(((Genre) item).getName());
        } else {
            throw new IllegalArgumentException("Object isn't of class Song, Artist, Album, or Genre.");
        }
    }

}
/* MetaData Key:
    Genre - 6
    Artist - 2
    Title - 7
    Year - 8
    Album Artist - 13
 */