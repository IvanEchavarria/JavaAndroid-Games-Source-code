/*
Android Memory Game  (Midterm)

Ivan Echavarria            2018/03/17

App Description : Android memory game

 */


package com.example.tech.androidmidterm;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


import android.os.CountDownTimer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    int[] ImageButtonList = new int[]{R.drawable.card_1c, R.drawable.card_1d, R.drawable.card_1h, R.drawable.card_1s,
                                      R.drawable.card_2c, R.drawable.card_2d, R.drawable.card_2h , R.drawable.card_2s,
                                      R.drawable.card_3c, R.drawable.card_3d, R.drawable.card_3h,   R.drawable.card_3s,
                                      R.drawable.card_4c, R.drawable.card_4d, R.drawable.card_4h, R.drawable.card_4s,
                                        R.drawable.card_5c, R.drawable.card_5d, R.drawable.card_5h, R.drawable.card_5s,
                                        R.drawable.card_6c, R.drawable.card_6d, R.drawable.card_6h, R.drawable.card_6s,
                                        R.drawable.card_7c, R.drawable.card_7d, R.drawable.card_7h, R.drawable.card_7s,
                                        R.drawable.card_8c, R.drawable.card_8d, R.drawable.card_8h, R.drawable.card_8s,
                                        R.drawable.card_9c, R.drawable.card_9d, R.drawable.card_9h, R.drawable.card_9s,
                                        R.drawable.card_10c, R.drawable.card_10d, R.drawable.card_10h, R.drawable.card_10s,
                                        R.drawable.card_11c,R.drawable.card_11d, R.drawable.card_11h, R.drawable.card_11s,
                                        R.drawable.card_12c, R.drawable.card_12d, R.drawable.card_12h, R.drawable.card_12s,
                                        R.drawable.card_13c, R.drawable.card_13d, R.drawable.card_13h, R.drawable.card_13s
    };

    private ImageButton one;
    private ImageButton two;
    private ImageButton three;
    private ImageButton four;
    private ImageButton five;
    private ImageButton six;
    private ImageButton seven;
    private ImageButton eight;
    private ImageButton nine;
    private ImageButton ten;
    private ImageButton eleven;
    private ImageButton twelve;
    private ImageButton thirteen;
    private ImageButton fourteen;
    private ImageButton fifteen;
    private ImageButton sixteen;

    private TextView timerText;
    private TextView scoreText;
    private TextView pickACardText;
    private int scoreValue = 0;

    private int[] duplicates = new int[8];
    private int[][] grid = new int[4][4];

    private int cardSelectionCounter = 0;
    private boolean[] cardBooleanArray = new boolean[16];
    private int cardIds[] = new int[2];
    private ImageButton selectedButtons[] = new ImageButton[2];
    private boolean[] cardDisabledArray = new boolean[16];

    private AlertDialog.Builder resetGame;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       timerText = findViewById(R.id.timer);
        scoreText = findViewById(R.id.scoreText);
        pickACardText = findViewById(R.id.pickACard);

        resetGame = new AlertDialog.Builder(this);


        scoreText.setText("Score: " + String.valueOf(0));
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerText.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {

                timerText.setText("times up!");
                resetGame.setTitle("Score: " + scoreValue);
                resetGame.setPositiveButton("Play Again?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recreate();
                    }
                });
                resetGame.show();

            }
        }.start();

        one = findViewById(R.id.image1);
        two = findViewById(R.id.image2);
        three = findViewById(R.id.image3);
        four = findViewById(R.id.image4);
        five = findViewById(R.id.image5);
        six = findViewById(R.id.image6);
        seven = findViewById(R.id.image7);
        eight = findViewById(R.id.image8);
        nine = findViewById(R.id.image9);
        ten = findViewById(R.id.image10);
        eleven = findViewById(R.id.image11);
        twelve = findViewById(R.id.image12);
        thirteen = findViewById(R.id.image13);
        fourteen = findViewById(R.id.image14);
        fifteen = findViewById(R.id.image15);
        sixteen = findViewById(R.id.image16);

        one.setOnClickListener(imageButton1);
        two.setOnClickListener(imageButton2);
        three.setOnClickListener(imageButton3);
        four.setOnClickListener(imageButton4);
        five.setOnClickListener(imageButton5);
        six.setOnClickListener(imageButton6);
        seven.setOnClickListener(imageButton7);
        eight.setOnClickListener(imageButton8);
        nine.setOnClickListener(imageButton9);
        ten.setOnClickListener(imageButton10);
        eleven.setOnClickListener(imageButton11);
        twelve.setOnClickListener(imageButton12);
        thirteen.setOnClickListener(imageButton13);
        fourteen.setOnClickListener(imageButton14);
        fifteen.setOnClickListener(imageButton15);
        sixteen.setOnClickListener(imageButton16);

        for(int i = 0; i < cardBooleanArray.length; i++)
        {
            cardBooleanArray[i] = false;
            cardDisabledArray[i] = false;
        }

        for(int i = 0; i < cardIds.length; i++)
        {
            cardIds[i] = -1;
        }

        // fill duplicates array with default value
        for(int i= 0; i < duplicates.length; i++)
        {
            duplicates[i] = -1;
        }

        //fill grid array with default value
        for(int i = 0; i < 4 ; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                grid[i][j] = -1;
            }
        }

       for(int i = 0; i < duplicates.length ; i++ )
        {
            int newRandom = selectRandomCards();

            randomGrid(newRandom);
        }

    }

    // Select 8 random cards from the list of images (ImageButtonList Array) and add them to an array called duplicates
    private int selectRandomCards()
    {

        int randomCard = (int) Math.floor(Math.random() * ImageButtonList.length);

        //If the duplicate index position is equal to -1 then there is no card there, so add a card
        for(int j = 0; j < duplicates.length ; j++)
        {
            if(duplicates[j] == -1)
            {
                duplicates[j] = randomCard;
                return randomCard;
            }
            else if(randomCard == duplicates[j])    // If position has a card and this card is equal to our random card then loop until we find a card that is not a duplicate
            {
                boolean isDuplicated = false;
                while(randomCard == duplicates[j] || isDuplicated)  // if random equals to a card we have or if any other card in the array is a duplicate of random then keep looping
                {
                    randomCard = (int) Math.floor(Math.random() * ImageButtonList.length);
                    //Check if this is valid number for our cards (No duplicated in our array)
                    for(int x = 0; x < duplicates.length ; x++)
                    {
                        if(randomCard == duplicates[x])    // Loop through duplicates and keep the loop going if we found two of the same card
                        {
                            isDuplicated = true;
                            break;
                        }
                        else if(duplicates[x] == -1)     // if we found a -1 then the position is open for the new card
                        {
                            isDuplicated = false;
                            break;
                        }
                        else
                        {
                            isDuplicated = false;
                        }
                    }
                }
            }
        }
        return randomCard;
    }

    int cardCount = 0;
    // Add the card number to a random position in the grid
    private void randomGrid(int cardNumber)
    {
        int row = (int) Math.floor(Math.random() * grid.length);  // Select two random positions in the grid
        int col = (int) Math.floor(Math.random() * grid.length);

        if(grid[row][col] == -1 && cardCount < 2)   //If the location we are at is -1 then is an empty position, we repeat this twice recursively.
        {
            grid[row][col] = cardNumber;
            cardCount++;

            if(cardCount >= 2)
            {
                cardCount = 0;
                return;
            }
            randomGrid(cardNumber);
        }
        else if(cardNumber == grid[row][col])   // If we have a collision with our same card dont replace it, just recurse for a new empty position
        {
            randomGrid(cardNumber);
        }
        else
        {
            randomGrid(cardNumber);   // Basically this else could be merge with the above as it does the same thing for colliding with a card
        }
    }


    // 16 button listeners for each Image Button in the grid. Not very efficient but faster time wise given the midterm time we had and unable to properly plan.

    View.OnClickListener imageButton1 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            //If this card has not been disabled then flip it back
            if(cardBooleanArray[0] && !cardDisabledArray[0])
            {
                cardBooleanArray[0] = false;
                cardSelectionCounter--;
                one.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[0]) // If the number of cards selected is less than 2 and this card has not been selected
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[0] = true;                           // This card has been selected, if clicked again it wont count as a new card selected
                cardIds[cardSelectionCounter] = ImageButtonList[grid[0][0]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = one;                 // Get this button in the selected array buttons
                cardSelectionCounter++;
                one.setImageResource(ImageButtonList[grid[0][0]]);    // Set the image of this card to whatever is in the grid.
                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);

                    }
                }
            }
        }
    };

    View.OnClickListener imageButton2 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
           //If this card has not been disabled then flip it back
            if(cardBooleanArray[1] && !cardDisabledArray[1])
            {
                cardBooleanArray[1] = false;
                cardSelectionCounter--;
                two.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[1])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[1] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[0][1]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = two;
                cardSelectionCounter++;
                two.setImageResource(ImageButtonList[grid[0][1]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }
    };

    View.OnClickListener imageButton3 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            //If this card has not been disabled then flip it back
            if(cardBooleanArray[2] && !cardDisabledArray[2])
            {
                cardBooleanArray[2] = false;
                cardSelectionCounter--;
                three.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[2])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[2] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[0][2]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = three;
                cardSelectionCounter++;
                three.setImageResource(ImageButtonList[grid[0][2]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }
        }

    };

    View.OnClickListener imageButton4 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[3] && !cardDisabledArray[3])
            {
                cardBooleanArray[3] = false;
                cardSelectionCounter--;
                four.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[3])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[3] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[0][3]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id.
                selectedButtons[cardSelectionCounter] = four;
                cardSelectionCounter++;
                four.setImageResource(ImageButtonList[grid[0][3]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton5 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[4] && !cardDisabledArray[4])
            {
                cardBooleanArray[4] = false;
                cardSelectionCounter--;
                five.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[4])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[4] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[1][0]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = five;
                cardSelectionCounter++;
                five.setImageResource(ImageButtonList[grid[1][0]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton6 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[5] && !cardDisabledArray[5])
            {
                cardBooleanArray[5] = false;
                cardSelectionCounter--;
                six.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[5])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[5] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[1][1]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = six;
                cardSelectionCounter++;
                six.setImageResource(ImageButtonList[grid[1][1]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton7 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[6] && !cardDisabledArray[6])
            {
                cardBooleanArray[6] = false;
                cardSelectionCounter--;
                seven.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[6])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[6] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[1][2]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = seven;
                cardSelectionCounter++;
                seven.setImageResource(ImageButtonList[grid[1][2]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton8 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[7] && !cardDisabledArray[7])
            {
                cardBooleanArray[7] = false;
                cardSelectionCounter--;
                eight.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[7])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[7] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[1][3]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = eight;
                cardSelectionCounter++;
                eight.setImageResource(ImageButtonList[grid[1][3]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton9 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[8] && !cardDisabledArray[8])
            {
                cardBooleanArray[8] = false;
                cardSelectionCounter--;
                nine.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[8])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[8] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[2][0]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = nine;
                cardSelectionCounter++;
                nine.setImageResource(ImageButtonList[grid[2][0]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton10 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[9] && !cardDisabledArray[9])
            {
                cardBooleanArray[9] = false;
                cardSelectionCounter--;
                ten.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[9])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[9] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[2][1]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = ten;
                cardSelectionCounter++;
                ten.setImageResource(ImageButtonList[grid[2][1]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton11 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[10] && !cardDisabledArray[10])
            {
                cardBooleanArray[10] = false;
                cardSelectionCounter--;
                eleven.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[10])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[10] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[2][2]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = eleven;
                cardSelectionCounter++;
                eleven.setImageResource(ImageButtonList[grid[2][2]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton12 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[11] && !cardDisabledArray[11])
            {
                cardBooleanArray[11] = false;
                cardSelectionCounter--;
                twelve.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[11])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[11] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[2][3]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = twelve;
                cardSelectionCounter++;
                twelve.setImageResource(ImageButtonList[grid[2][3]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton13 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[12] && !cardDisabledArray[12])
            {
                cardBooleanArray[12] = false;
                cardSelectionCounter--;
                thirteen.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[12])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[12] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[3][0]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = thirteen;
                cardSelectionCounter++;
                thirteen.setImageResource(ImageButtonList[grid[3][0]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton14 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[13] && !cardDisabledArray[13])
            {
                cardBooleanArray[13] = false;
                cardSelectionCounter--;
                fourteen.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[13])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[13] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[3][1]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = fourteen;
                cardSelectionCounter++;
                fourteen.setImageResource(ImageButtonList[grid[3][1]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton15 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[14] && !cardDisabledArray[14])
            {
                cardBooleanArray[14] = false;
                cardSelectionCounter--;
                fifteen.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[14])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[14] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[3][2]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = fifteen;
                cardSelectionCounter++;
                fifteen.setImageResource(ImageButtonList[grid[3][2]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }

        }

    };

    View.OnClickListener imageButton16 = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(cardBooleanArray[15] && !cardDisabledArray[15])
            {
                cardBooleanArray[15] = false;
                cardSelectionCounter--;
                sixteen.setImageResource(R.drawable.cardback);
            }
            else if(cardSelectionCounter < 2 && !cardBooleanArray[15])
            {
                pickACardText.setText("Now Pick Another Card");
                cardBooleanArray[15] = true;
                cardIds[cardSelectionCounter] = ImageButtonList[grid[3][3]]; // Grab the Id of this from the grid, we need the name of the card to compare with another id
                selectedButtons[cardSelectionCounter] = sixteen;
                cardSelectionCounter++;
                sixteen.setImageResource(ImageButtonList[grid[3][3]]);

                if(cardSelectionCounter == 2)                         // If this is card number 2 then we check for equality
                {
                    if(cardIds[0] == cardIds[1])                      // Check if IDs are equal to add points and disable this card and the other selected.
                    {
                        cardsEqual();
                    }
                    else
                    {
                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cardsNotEqual();
                            }
                        }, 100);
                    }
                }
            }


        }

    };

    // If the cards matched then we make them invisible after a delay

    private void makeInvisible()
    {
        selectedButtons[0].setVisibility(View.INVISIBLE);
        selectedButtons[1].setVisibility(View.INVISIBLE);
    }

    //if cards are equal then add score, set the buttons to no clickable and call the function to make them invisible
    private void cardsEqual()
    {
        pickACardText.setText("Right!");
        scoreValue += 10;
        cardSelectionCounter = 0;
        selectedButtons[0].setClickable(false);       // Disable ability to be clickable
        selectedButtons[1].setClickable(false);
        scoreText.setText("Score: " + String.valueOf(scoreValue));

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                makeInvisible();
            }
        }, 100);
    }

    //If cards not equal then reset the cards back to the default state.
    private void cardsNotEqual()
    {
        pickACardText.setText("Wrong! pick again...");
        cardSelectionCounter = 0;
        selectedButtons[0].setImageResource(R.drawable.cardback);
        selectedButtons[1].setImageResource(R.drawable.cardback);
        for(int i = 0; i < cardBooleanArray.length; i++)
        {
            cardBooleanArray[i] = false;
            cardDisabledArray[i] = false;
        }
    }
}




