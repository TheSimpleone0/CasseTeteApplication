package com.example.bazile.p8cassetete;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.bazile.p8cassetete.levels.Bloc;

import java.util.ArrayList;
import java.util.Random;

public class CasseTeteView2 extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    Bitmap casseVide, casseRouge, casseJaune,
            casseTurquoise, Fondecran, casseBleu,
            casseBlack, casseVert, casseOrange,casseMarron;

    Thread cv_thread;

    int carteCentreGauche, carteCentreHaut;

    boolean bloquer;




    // le  score du joueur
    int Score = 0, nbPiece = 0;

    Random r = new Random();

    /**
     * Dessin
     **/
    Paint paint;
    /**
     * Variable global qui va récuperer les position i et j selon le drop
     **/
    int finalPostion_x, finalPosition_y; // global
    ArrayList<com.example.bazile.p8cassetete.levels.Level> levels;
    com.example.bazile.p8cassetete.levels.Level currentLevel;
    private Resources Res;
    private Context Context;
    private boolean go = true; // Pour le draw
    private SurfaceHolder holder;

    /**
     * Fonction pricipale
     **/
    public CasseTeteView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);

        Context = context;
        Res = Context.getResources();
        Fondecran = BitmapFactory.decodeResource(Res, R.drawable.fond_ecran);
        casseBleu = BitmapFactory.decodeResource(Res, R.drawable.cube);
        casseTurquoise = BitmapFactory.decodeResource(Res, R.drawable.cube1);
        casseRouge = BitmapFactory.decodeResource(Res, R.drawable.cube2);
        casseJaune = BitmapFactory.decodeResource(Res, R.drawable.cube3);
        casseVide = BitmapFactory.decodeResource(Res, R.drawable.cube4);
        casseBlack = BitmapFactory.decodeResource(Res, R.drawable.cube5);
        casseVert = BitmapFactory.decodeResource(Res, R.drawable.cube6);
        casseOrange = BitmapFactory.decodeResource(Res, R.drawable.cube8);
        casseMarron = BitmapFactory.decodeResource(Res, R.drawable.cube7);

        initparameters();
        cv_thread = new Thread(this);

        setFocusable(true);
    }

    public void initparameters() {

        paint = new Paint();
        paint.setColor(0xff0000);
        paint.setDither(true);
        paint.setColor(0xFFFFFF00);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        paint.setTextAlign(Paint.Align.LEFT);

        bloquer = false;
        levels = new ArrayList<>();

        adding_levels();
        Log.e("initparameters levels", "" + levels.size());
        int choix_level  = r.nextInt(3);

        //Log.d("choix", "" + choix_level);


        currentLevel = levels.get(choix_level);
        carteCentreHaut = (getHeight() - currentLevel.carteHeight * currentLevel.carteTileSize) / 2;
        carteCentreGauche = (getWidth() - currentLevel.carteWidth * currentLevel.carteTileSize) / 2;

        // currentLevel.list_bloc.get(0).setPosX(carteCentreGauche + 4 * currentLevel.carteTileSize);
        // currentLevel.list_bloc.get(0).setPosY(carteCentreGauche + 3 * currentLevel.carteTileSize);


        if ((cv_thread != null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.e("-FCT-", "cv_thread.start()");
        }
    }




    // CREATION DES PIECE PUIS ON LES AJOUTE AU NIVEAUX
    private void adding_firstlevel() {
        int[][] blc0 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc1 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc2 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc3 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc4 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc5 = new int[currentLevel.carteHeight][currentLevel.carteWidth];

        initzeros(blc0);
        initzeros(blc1);
        initzeros(blc2);
        initzeros(blc3);
        initzeros(blc4);
        initzeros(blc5);

        com.example.bazile.p8cassetete.levels.Level level1 = new com.example.bazile.p8cassetete.levels.Level();
        // premiers bloc
        blc0[0][0] = 2;
        blc0[0][1] = 2;
        blc0[0][2] = 2;
        blc0[1][1] = 2;

        level1.add_Bloc(90, 1764, 2, 3, blc0, 2);
        // 2 bloc
        blc1[0][0] = 3;
        blc1[1][0] = 3;
        blc1[2][0] = 3;
        blc1[1][1] = 3;
        blc1[2][1] = 3;

        level1.add_Bloc(1086, 1688, 3, 2, blc1, 3);
        // 3 bloc
        blc2[0][0] = 4;
        blc2[0][1] = 4;
        blc2[0][2] = 4;
        blc2[0][3] = 4;

        level1.add_Bloc(53, 412, 1, 4, blc2, 4);
        // 4 bloc
        blc3[0][0] = 5;
        blc3[0][1] = 5;
        blc3[0][2] = 5;
        blc3[1][2] = 5;

        level1.add_Bloc(611, 412, 2, 3, blc3, 5);
        // 5 bloc
        blc4[0][0] = 6;
        blc4[0][1] = 6;
        blc4[1][0] = 6;
        blc4[1][1] = 6;
        level1.add_Bloc(642, 1763, 2, 2, blc4, 6);
        // 6 bloc
        blc5[0][0] = 7;
        blc5[0][1] = 7;
        blc5[1][1] = 7;
        blc5[2][1] = 7;

        level1.add_Bloc(1136, 378, 3, 2, blc5, 7);
        levels.add(level1);
    }

    // CREATION DES PIECE PUIS ON LES AJOUTE AU NIVEAUX
    private void adding_secondlevel() {
        int[][] blc0 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc1 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc2 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc3 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc4 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc5 = new int[currentLevel.carteHeight][currentLevel.carteWidth];

        initzeros(blc0);
        initzeros(blc1);
        initzeros(blc2);
        initzeros(blc3);
        initzeros(blc4);
        initzeros(blc5);

        com.example.bazile.p8cassetete.levels.Level level1 = new com.example.bazile.p8cassetete.levels.Level();
        // premiers bloc
        blc0[1][0] = 2;
        blc0[1][1] = 2;
        blc0[0][1] = 2;
        blc0[0][0] = 2;

        level1.add_Bloc(90, 1764, 2, 2, blc0, 2);
        // 2 bloc
        blc1[0][0] = 3;
        blc1[1][0] = 3;
        blc1[2][0] = 3;
        blc1[3][0] = 3;
        blc1[0][1] = 3;
        blc1[0][2] = 3;
        blc1[0][3] = 3;

        level1.add_Bloc(1086, 1688, 4, 4, blc1, 3);
        // 3 bloc
        blc2[0][0] = 4;
        blc2[0][1] = 4;
        blc2[0][2] = 4;

        level1.add_Bloc(53, 412, 1, 3, blc2, 4);
        // 4 bloc
        blc3[0][0] = 5;
        blc3[0][1] = 5;
        blc3[0][2] = 5;
        blc3[0][3] = 5;
        blc3[0][4] = 5;

        level1.add_Bloc(611, 412, 1, 5, blc3, 5);
        // 5 bloc
        blc4[1][0] = 6;
        blc4[2][0] = 6;
        blc4[0][1] = 6;
        blc4[1][1] = 6;
        blc4[2][1] = 6;
        blc4[3][1] = 6;
        level1.add_Bloc(642, 1763, 4, 2, blc4, 6);
        // 6 bloc
        levels.add(level1);
    }

    // CREATION DES PIECE PUIS ON LES AJOUTE AU NIVEAUX
    private void adding_thirdlevel() {
        int[][] blc0 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc1 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc2 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc3 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc4 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc5 = new int[currentLevel.carteHeight][currentLevel.carteWidth];
        int[][] blc6 =  new int[currentLevel.carteHeight][currentLevel.carteWidth];

        initzeros(blc0);
        initzeros(blc1);
        initzeros(blc2);
        initzeros(blc3);
        initzeros(blc4);
        initzeros(blc5);
        initzeros(blc6);

        com.example.bazile.p8cassetete.levels.Level level1 = new com.example.bazile.p8cassetete.levels.Level();
        // premiers bloc
        blc0[0][0] = 2;
        level1.add_Bloc(90, 1764, 1, 1, blc0, 2);
        // 2 bloc
        blc1[0][0] = 3;
        blc1[1][0] = 3;
        blc1[2][0] = 3;
        blc1[1][1] = 3;

        //level1.add_Bloc(1086, 1688, 2, 3, blc1, 3);
        level1.add_Bloc(1086, 1688, 3, 2, blc1, 3);
        // 3 bloc
        blc2[0][0] = 4;
        blc2[0][1] = 4;
        blc2[0][2] = 4;
        blc2[1][2] = 4;
        level1.add_Bloc(53, 412, 2, 3, blc2, 4);
        // 4 bloc

        blc3[0][1] = 5;
        blc3[1][0] = 5;
        blc3[2][0] = 5;
        blc3[0][0] = 5;

        //level1.add_Bloc(611, 412, 3, 2, blc3, 5);
        level1.add_Bloc(611, 412, 3, 2, blc3, 5);
        // 5 bloc
        blc4[0][1] = 6;
        blc4[1][0] = 6;
        blc4[1][1] = 6;
        blc4[1][2] = 6;
        level1.add_Bloc(642, 1763, 2, 3, blc4, 6);
        // 6 bloc
        blc5[0][0] = 7;
        blc5[0][1] = 7;
        blc5[1][1] = 7;
        level1.add_Bloc(1136, 378, 2, 2, blc5, 7);

        blc6[0][0] = 8;
        blc6[0][1] = 8;
        blc6[0][2] = 8;
        blc6[0][3] = 8;
        blc6[0][4] = 8;
        level1.add_Bloc(426, 1508, 1, 5, blc6, 8);
        levels.add(level1);
    }

    // on appelle autant
    private void adding_levels() {
        adding_firstlevel();
        adding_secondlevel();
        adding_thirdlevel();
    }

    public void initzeros(int[][] blocs) {

        for (int i = 0; i < currentLevel.carteWidth; i++) {
            for (int j = 0; j < currentLevel.carteHeight; j++) {
                blocs[i][j] = 0;
            }
        }
    }

    /**
     * Dessin du bloque ou on doit placer les platefomes
     **/
    private void paintCarte(Canvas canvas) {
        int i, j;
        for (i = 0; i < 5; i++)
            for (j = 0; j < 5; j++) {
                switch (currentLevel.Tab[i][j]) {
                    case 0:
                        canvas.drawBitmap(casseVide, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 1:
                        canvas.drawBitmap(casseVide, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 2:
                        canvas.drawBitmap(casseTurquoise, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 3:
                        canvas.drawBitmap(casseJaune, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 4:
                        canvas.drawBitmap(casseRouge, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 5:
                        canvas.drawBitmap(casseBleu, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 6:
                        canvas.drawBitmap(casseBlack, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 7:
                        canvas.drawBitmap(casseVert, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);

                    case 8:
                        canvas.drawBitmap(casseOrange, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                    case 9:
                        canvas.drawBitmap(casseMarron, carteCentreGauche + j * currentLevel.carteTileSize, carteCentreHaut + i * currentLevel.carteTileSize, null);
                        break;
                }
            }
    }



    /**
     * Dessin des briques  en mode statique non par un tableau aléatoires
     **/
    private void paintBloc(Canvas canvas) {
        for (Bloc bloc : currentLevel.list_bloc) {
            for (int i = 0; i < currentLevel.carteHeight; i++) {
                for (int j = 0; j < currentLevel.carteWidth; j++) {
                    switch (bloc.getForme()[i][j]) {
                        case 1:
                            canvas.drawBitmap(casseTurquoise, bloc.getPosX() + j * currentLevel.carteTileSize, bloc.getPosY() + i * currentLevel.carteTileSize, null);
                            break;
                        case 2:
                            canvas.drawBitmap(casseTurquoise, bloc.getPosX() + j * currentLevel.carteTileSize, bloc.getPosY() + i * currentLevel.carteTileSize, null);
                            break;
                        case 3:
                            canvas.drawBitmap(casseJaune, bloc.getPosX() + j * currentLevel.carteTileSize, bloc.getPosY() + i * currentLevel.carteTileSize, null);
                            break;
                        case 4:
                            canvas.drawBitmap(casseRouge, bloc.getPosX() + j * currentLevel.carteTileSize, bloc.getPosY() + i * currentLevel.carteTileSize, null);
                            break;
                        case 5:
                            canvas.drawBitmap(casseBleu, bloc.getPosX() + j * currentLevel.carteTileSize, bloc.getPosY() + i * currentLevel.carteTileSize, null);
                            break;
                        case 6:
                            canvas.drawBitmap(casseBlack, bloc.getPosX() + j * currentLevel.carteTileSize, bloc.getPosY() + i * currentLevel.carteTileSize, null);
                            break;
                        case 7:
                            canvas.drawBitmap(casseVert, bloc.getPosX() + j * currentLevel.carteTileSize, bloc.getPosY() + i * currentLevel.carteTileSize, null);
                            break;
                        case 8:
                            canvas.drawBitmap(casseOrange, bloc.getPosX() + j * currentLevel.carteTileSize, bloc.getPosY() + i * currentLevel.carteTileSize, null);
                            break;
                        case 9:
                            canvas.drawBitmap(casseMarron, bloc.getPosX() + j * currentLevel.carteTileSize, bloc.getPosY() + i * currentLevel.carteTileSize, null);
                            break;

                    }
                }
            }
        }

    }


    private void nDraw(Canvas canvas) {
        canvas.drawBitmap(Fondecran, 0, 0, null);
        paintCarte(canvas);
        paintBloc(canvas);


    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("-> FCT <-", "surfaceChanged " + width + " - " + height);
        initparameters();
         afficher();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceCreated");
    }


    @Override
    public void run() {
        Canvas c = null;
        while (go) {
            try {
                cv_thread.sleep(40);
                try {
                    c = holder.lockCanvas(null);
                    nDraw(c);
                    //Log.d("position des pieces:", "on vas :");
                    //affichepos();
                    //currentLevel.affiche_Tab();
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch (Exception e) {
                Log.e("-> RUN <-", "PB DANS RUN");
            }

        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int positionX = (int) event.getX();
        int positionY = (int) event.getY();


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                for (Bloc b : currentLevel.list_bloc) {
                    if (positionX >= b.getPosX() && positionX < b.getPosX() + 2 * currentLevel.carteTileSize
                            && b.getPosY() <= positionY && positionY < b.getPosY() + 2 * currentLevel.carteTileSize)
                        b.setEst_touche(true);
                    Log.d("onTouchEvent : ", "est touché");
                }
                break;

            case MotionEvent.ACTION_MOVE:
                for (Bloc b : currentLevel.list_bloc) {
                    if (b.isEst_touche()) {
                        restart(currentLevel.list_bloc.indexOf(b) + 2);
                        b.setPosX(positionX);
                        b.setPosY(positionY);
                        Log.d("Position"," "+b.getPosX()+" "+b.getPosY());
                    }
                }

                for (Bloc b : currentLevel.list_bloc) {
                    if (InBox(b.getPosX(), b.getPosY())) {
                        b.setPosX(carteCentreGauche + GetValue(finalPostion_x) * currentLevel.carteTileSize);
                        b.setPosY(carteCentreHaut + GetValue(finalPosition_y) * currentLevel.carteTileSize);

                        updateTable(b.getForme(), GetValue(finalPosition_y), GetValue(finalPostion_x), b.getId(),b.getTailleX(),b.getTailleY());
                        Log.d("Inbox", "is inbox");


                        Log.d("line", "" + GetValue(finalPostion_x) + " " + b.getPosX());
                        Log.d("row ", "" + GetValue(finalPosition_y) + " " + b.getPosY());

                       b.setEst_fixe(true);
                    } else {
                       b.setEst_fixe(false);
                    }
                }

                break;


            case MotionEvent.ACTION_UP:
                for (Bloc b : currentLevel.list_bloc) {
                    b.setEst_touche(false);
                }
                comptageVide();
                Log.d("nbpiece", " "+nbPiece);
                afficher();
                YouWin();
                break;
        }

        invalidate();
        return true;
    }

    /**
     * fonction qui vérifie si tu es dedans je dois la refaire il vérifie pour un point de la pièce.
     **/
    public boolean InBox(int x, int y) {
        int i, j;

        for (i = 0; i < currentLevel.carteHeight; i++) {
            for (j = 0; j < currentLevel.carteWidth; j++) {
                if (x > carteCentreGauche - currentLevel.carteTileSize
                        && x <= carteCentreGauche + i * currentLevel.carteTileSize && y > carteCentreHaut - currentLevel.carteTileSize
                        && y <= carteCentreHaut + j * currentLevel.carteTileSize) {

                    Log.d("Setup :", " "+i+" "+j);
                    SetValue(i, j);
                    return true;
                } else continue;
            }

        }
        Log.d("Inbox :", "Hors limite");
        return false;
    }

    /**
     * fonction qui change la pièce si il est en dedans.
     **/
    public void updateTable(int Tab[][], int x, int y, int id,int TailleX,int TailleY) {
        int i, j;


        if (x >= 0 && x <= currentLevel.carteHeight - TailleX && y >= 0 && y <= currentLevel.carteWidth - TailleY) {
            for (i = x; i < TailleX + x; i++) {
                for (j = y; j < TailleY + y; j++) {
                    if (Tab[i - x][j - y] == id && currentLevel.Tab[i][j] == 1) {
                        currentLevel.Tab[i][j] = Tab[i - x][j - y];

                    }
                        else Log.d("Update :", "Condition");
                }
            }

        } else {

            Log.d("Update2 :", "No");
        }


    }

    /**
     * Inutile
     **/


    public void raseTable(Bloc temp, int x, int y, int id) {
        int i, j;
        if (x >= 0 && x <= currentLevel.carteHeight - temp.getTailleX() && y >= 0 && y <= currentLevel.carteWidth - temp.getTailleY()) {
            for (i = x; i < temp.getTailleX() + x; i++) {
                for (j = y; j < temp.getTailleY() + y; j++) {
                    if (temp.getForme()[i - x][j - y] == id)
                        currentLevel.Tab[i][j] = 1;
                }
            }
        }

    }

    /**
     * fonction qui éfface si la pièce n'est pas dans le complétement dans le bloc.
     **/
    public void restart(int id) {
        int i, j;
        for (i = 0; i < currentLevel.carteHeight; i++)
            for (j = 0; j < currentLevel.carteWidth; j++) {
                if (currentLevel.Tab[i][j] == id)
                    currentLevel.Tab[i][j] = 1;
            }
    }

    /**
     * Tu devine.
     **/
    public boolean YouWin() {
        for (Bloc b : currentLevel.list_bloc) {
            if (!b.isEst_fixe())
                return false;
        }

        if (nbPiece != 0) {
            return false;
        } else
            return true;

    }

    /**
     * Savoir si il y a des 1.
     **/
    public int comptageVide() {
        int i, j, cmt = 0;

        for (i = 0; i < currentLevel.carteHeight; i++) {
            for (j = 0; j < currentLevel.carteWidth; j++) {
                if (currentLevel.Tab[i][j] == 1) {
                    cmt++;
                }
            }
        }
        return nbPiece = cmt;
    }

    /**
     * Outil neccessaire.
     **/
    public void SetValue(int x, int y) {
        this.finalPostion_x = x;
        this.finalPosition_y = y;
    }

    public int GetValue(int x) {
        return x;
    }

    public void afficher() {
        int i, j;
        System.out.println("Tableau principal");
        for (i = 0; i < currentLevel.carteWidth; i++) {
            for (j = 0; j < currentLevel.carteHeight; j++)
                System.out.print(currentLevel.Tab[i][j]);
            System.out.println("");
        }


    }

    public void afficher(Bloc d) {
        int i, j;
        System.out.println("Tableau de bloc");
        for (i = 0; i < currentLevel.carteWidth; i++) {
            for (j = 0; j < currentLevel.carteHeight; j++)
                System.out.print(d.getForme()[i][j]);
            System.out.println("");
        }
    }
}
