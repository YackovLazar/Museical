package com.ladswithlaptops.museical.classes;

import android.media.MediaMetadataRetriever;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class Mapper {
    public static final HashSet<Song> mSongs = new HashSet<>();
    public static final HashMap<String, Artist> mArtists = new HashMap<>();
    public static final HashSet<Album> mAlbums = new HashSet<>();
    public static final HashMap<String, Genre> mGenres = new HashMap<>();

    /**
     * Create lists for Songs, Artists, Albums, and Genres that can then be queried
     */
    public static void mapSongs() {
        MediaMetadataRetriever metadata = new MediaMetadataRetriever();
        String path, title, albumStr;
        Album album;
        String[] genresParsed, artistsParsed;
        Song song;

        // Locates the res folder which contains the audio files
        File folder = new File("/Museical/app/src/main/res/raw");  // FIXME Replace with proper res folder
        // Creates an array of audio files to be processed
        File[] listOfFiles = folder.listFiles();

        // TODO Process every file
        for (File f : listOfFiles) {  // There are still files in dir
            metadata.setDataSource(f.getPath());

            // Parse Genre and Artist Metadata which could contain multiple values with deliminators
            genresParsed = parseString(metadata.extractMetadata(6));
            artistsParsed = parseString(metadata.extractMetadata(2));
            title = metadata.extractMetadata(7);
            album = new Album.Builder().title(metadata.extractMetadata(1)).build();

            // Create Song
            song = new Song.Builder()
                    .path(f.getPath())
                    .title(title)
                    .album(album)
                    .year((short) Integer.parseInt(metadata.extractMetadata(8)))
                    .build();
            mSongs.add(song);  // REVIEW Does return value need to be checked?

            // Map song and its elements
            // Map Genres
            for (String gStr : genresParsed) {
                gStr = gStr.trim();
                Genre currGenre = mGenres.get(gStr);
                if (currGenre != null) {
                    currGenre.addSong(song);
                    mGenres.put(gStr, currGenre);  // REVIEW Is this necessary
                }
                song.addGenre(currGenre); // Adding Genre to LinkedList to be put into song
            }
            // Map Artists
            for (String aStr : artistsParsed) {
                aStr = aStr.trim();
                Artist currArtist = mArtists.get(aStr);
                if (currArtist != null) {
                    currArtist.addSong(song);
                    mArtists.put(aStr, currArtist);  // REVIEW Is this necessary
                }
                song.addArtist(currArtist);  // Adding Artist to LinkedList to be put into song
            }
            mAlbums.add(album);
            mSongs.add(song);
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
            throw new NullPointerException();  // REVIEW Should this throw an error?
        String[] parsed;

        if (!toParse.equals("")) {
            String[] deliminators = {";", "//"};  // REVIEW deliminator options
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
            throw new IllegalArgumentException("Object isn't an instance of Song, Artist, Album, or Genre.");
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