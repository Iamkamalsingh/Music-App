package com.singhkamal.mediaapp;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private ImageView playPauseButton;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private ImageView ic_prev;
    private ImageView ic_next;
    private TextView currentTime;
    private TextView totalDuration;
    private int currentSongIndex = 0;
    private final int[] songs = {R.raw.music1, R.raw.music2, R.raw.music3, R.raw.music4, R.raw.music5, R.raw.music6, R.raw.music7};

    
    
    
    
    
    
    
    
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        seekBar = findViewById(R.id.seekBar);
        playPauseButton = findViewById(R.id.playPauseButton);
        ic_prev = findViewById(R.id.ic_prev);
        ic_next = findViewById(R.id.ic_next);
        currentTime = findViewById(R.id.currentTime);
        totalDuration = findViewById(R.id.totalDuration);

//from


        // Add this line in your onCreate method after initializing the views
        setInitialMusicName();

        // Add this line to get reference to the TextView for music name
        TextView musicNameTextView = findViewById(R.id.musicName);

        if (playPauseButton == null || ic_prev == null || ic_next == null || musicNameTextView == null) {
            // Handle the case where views are not found
            Toast.makeText(this, "Views not found", Toast.LENGTH_SHORT).show();
            return;
        }

        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);

        // Update the music name
        musicNameTextView.setText(getMusicName(currentSongIndex));

        //to here


        if (playPauseButton == null || ic_prev == null || ic_next == null) {
            // Handle the case where views are not found
            Toast.makeText(this, "Views not found", Toast.LENGTH_SHORT).show();
            return;
        }

        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);

        setupSeekBar();
        setupPlayPauseButton();
        setupPrevNextButtons();
        // Call updateSeekBar for the first time
        updateSeekBar();




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    updateDurationTextViews(); // Update duration while seeking
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            playNextSong(); // Automatically play the next song

            updatePlayPauseButton();
            seekBar.setProgress(0);
            updateDurationTextViews();
        });
    }

    // for music name---->
    private void setInitialMusicName() {
        // Assuming you have an array of music names corresponding to each song
        String[] musicNames = {"Adhuro_Katha_-_Mc_Flo_(2018)_(Lyrics_on_screen)(144p).mp4.vm", "Adhuro_Prem_Lyrics_-_Axix_Band___Nepali_Lyrics%F0%9F%8E%B5(720p).mp4.vm",
                "DJ_Snake_-_Let_Me_Love_You_ft._Justin_Bieber_(Official_Video)(1080p).mp4.vm",
                "Har_Har_Shambhu_Shiv_Mahadeva___sanand_manand_vane___Abhilipsa_Panda___Jeetu_Sharma___shiv_stotra(720p).mp4.vm",
                "Post_Malone_-_rockstar_ft._21_Savage(1080p).mp4.vm", "Sushant_KC_-_Muskurayera_(Official_Lyrics_Video)(144p).mp4.vm",
                "Sushant_KC_-_Risaune_Bhaye(720p).mp4.vm"};

        // Set the initial music name based on the current song index
        setMusicName(musicNames[currentSongIndex]);
    }

    private void setMusicName(String musicName) {
        runOnUiThread(() -> {
            // Assuming you have a TextView with the ID musicNameTextView
            TextView musicNameTextView = findViewById(R.id.musicName);
            if (musicNameTextView != null) {
                musicNameTextView.setText(musicName);

                musicNameTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                musicNameTextView.setSingleLine(true);
                // Infinite marquee loop
                musicNameTextView.setMarqueeRepeatLimit(-1);

                // Programmatically start marquee
                musicNameTextView.postDelayed(() -> {
                    musicNameTextView.setSelected(true);
                }, 1000);
            }
        });
    }
//       <------------




    private void playPrevSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
        } else {
            currentSongIndex = songs.length - 1;
        }
        mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
        playMusic();
        // for music name
        setMusicName(getMusicName(currentSongIndex));
    }

    private void playNextSong() {
        if (currentSongIndex < songs.length - 1) {
            currentSongIndex++;
        } else {
            currentSongIndex = 0;
        }
        mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
        playMusic();
        updateDurationTextViews();
        //for music name
        setMusicName(getMusicName(currentSongIndex));
    }


    // for music  name
    // Add this method to get the music name based on the index
    private String getMusicName(int index) {
        // Assuming you have an array of music names corresponding to each song
        String[] musicNames = {"Adhuro_Katha_-_Mc_Flo_(2018)_(Lyrics_on_screen)(144p).mp4.vm", "Adhuro_Prem_Lyrics_-_Axix_Band___Nepali_Lyrics%F0%9F%8E%B5(720p).mp4.vm",
                "DJ_Snake_-_Let_Me_Love_You_ft._Justin_Bieber_(Official_Video)(1080p).mp4.vm",
                "Har_Har_Shambhu_Shiv_Mahadeva___sanand_manand_vane___Abhilipsa_Panda___Jeetu_Sharma___shiv_stotra(720p).mp4.vm",
                "Post_Malone_-_rockstar_ft._21_Savage(1080p).mp4.vm", "Sushant_KC_-_Muskurayera_(Official_Lyrics_Video)(144p).mp4.vm",
                "Sushant_KC_-_Risaune_Bhaye(720p).mp4.vm"};

        // Return the music name based on the index
        return musicNames[index];
    }

    private void updateSeekBar() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(currentPosition);
            currentTime.setText(millisecondsToTime(currentPosition));

            int totalTime = mediaPlayer.getDuration();
            if (totalTime != seekBar.getMax()) {
                seekBar.setMax(totalTime);
                totalDuration.setText(millisecondsToTime(totalTime));
            }

            handler.postDelayed(this::updateSeekBar, 1000);
        }
    }


    private void setupSeekBar() {
        int totalTime = mediaPlayer.getDuration();
        seekBar.setMax(totalTime);
        updateDurationTextViews();
        updateSeekBar();
    }





    private void updateDurationTextViews() {
        runOnUiThread(() -> {
            // Update current time TextView
            String currentTimeString = millisecondsToTime(mediaPlayer.getCurrentPosition());
            currentTime.setText(currentTimeString);

            // Update total duration TextView
            int totalTime = mediaPlayer.getDuration();
            String totalTimeString = millisecondsToTime(totalTime);
            totalDuration.setText(totalTimeString);
        });
    }

    @SuppressLint("DefaultLocale")
    private String millisecondsToTime(int milliseconds) {
        int seconds = milliseconds / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    private void setupPlayPauseButton() {
        playPauseButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                pauseMusic();
            } else {
                playMusic();
            }
        });
    }

    private void playMusic() {
        mediaPlayer.start();
        updatePlayPauseButton();
        updateSeekBar();
    }

    private void pauseMusic() {
        mediaPlayer.pause();
        updatePlayPauseButton();
    }

    private void setupPrevNextButtons() {
        ic_prev.setOnClickListener(v -> playPrevSong());
        ic_next.setOnClickListener(v -> playNextSong());
    }

    private void updatePlayPauseButton() {
        runOnUiThread(() -> {
            if (mediaPlayer.isPlaying()) {
                playPauseButton.setImageResource(R.drawable.ic_pause);
            } else {
                playPauseButton.setImageResource(R.drawable.ic_play);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}





































/*import static com.alkalising.media app.R.id.playPauseButton;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private Button playPauseButton;
    private MediaPlayer mediaPlayer;
    private ToggleButton toggleButton;
    private boolean isPlaying = false;
    private Handler handler;
    private ImageView imageView;
    private int currentSongIndex = 0;
    private ImageButton imageButton;

    // Assume you have a list of song resource IDs
    private int[] songs = {R.raw.music1, R.raw.music2, R.raw.music3,R.raw.music4};

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the handler
        handler = new Handler();

        // Reference buttons from XML layout

        seekBar = findViewById(R.id.seekBar);
        ImageButton playPauseButton = findViewById(R.id.playPauseButton);

        mediaPlayer = MediaPlayer.create(this, R.raw.music2);
        mediaPlayer = MediaPlayer.create(this, R.raw.music1);
        ImageView ic_prev=findViewById(R.id.ic_prev);
        ImageView ic_next=findViewById(R.id.ic_next);



        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);

        setupSeekBar();

        ic_next.setImageResource(R.drawable.ic_next);
        ic_prev.setImageResource(R.drawable.ic_prev);


        // Set click listeners for Button

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseMusic();
                } else {
                    playMusic();
                }
            }
        });







        ImageButton playPauseButton = findViewById(R.id.playPauseButton);
        final boolean[] isPlaying = {false};

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying[0]) {
                    playPauseButton.setImageResource(R.drawable.ic_play);
                    // Pause the music
                } else {
                    playPauseButton.setImageResource(R.drawable.ic_pause);
                    // Start or resume the music
                }
                isPlaying[0] = !isPlaying[0];
            }
        });







       /* ToggleButton playPauseButton = findViewById(R.id.playPauseButton);

        playPauseButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Set pause icon when the button is checked (playing)
                    playPauseButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_pause, 0, 0);
                    // Add logic to start playing
                } else {
                    // Set play icon when the button is unchecked (paused)
                    playPauseButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_play, 0, 0);
                    // Add logic to pause
                }
            }
        });
*/








     /*   ic_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrevSong();
            }
        });

        ic_next
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextSong();
            }
        });






        // Set listener for user updates on SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // If the change is from the user, seek to the specified position
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }
        });

        // Set completion listener to handle end of playback
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Playback completed, update UI
                playPauseButton.setTextDirection(Integer.parseInt("Play"));
                isPlaying[0] = false;
                seekBar.setProgress(0);
            }
        });
    }


//for play previous music
    private void playPrevSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
        } else {
            // Wrap around to the last song if already at the first song
            currentSongIndex = songs.length - 1;
        }
        mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
        playMusic();
    }


  // for play next songs
    private void playNextSong() {
        if (currentSongIndex < songs.length - 1) {
            currentSongIndex++;
        } else {
            // Wrap around to the first song if already at the last song
            currentSongIndex = 0;
        }
        mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
        playMusic();
    }





    private void setupSeekBar() {
        int totalTime = mediaPlayer.getDuration();
        seekBar.setMax(totalTime);

        // Update seek bar
        updateSeekBar();
    }

    private void updateSeekBar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateSeekBar();
            }
        }, 1000); // Update every 1000 milliseconds (1 second)
    }

    private void playMusic() {
        mediaPlayer.start();
        playPauseButton.setText("Pause");
        isPlaying = true;
        updateSeekBar();
    }

    private void pauseMusic() {
        mediaPlayer.pause();
        playPauseButton.setText("Play");
        isPlaying = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}

*/

