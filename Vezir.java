
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Vezir {

    public static void main(String[] args) {
        int n = 0;

        // Geçerli bir n değeri girilmesi bekleniyor

        while (n < 2) {
            try {
                System.out.println("N'in 1 den büyük olduğu bir değer giriniz : ");
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(System.in)
                );
                n = Integer.parseInt(br.readLine());
            } catch (NumberFormatException ex) {
                System.out.println("Sayı değil !");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        System.out.println( n + " x " + n + " tahta için " + n + " vezir hesaplanıyor...");

        int solutions;

        solutions = Nvezircozum(n);


        // Print result
        System.out.println(n + "-Vezir için " + solutions +
                " çözüm bulundu.");

    }

    /**
     * N x N tahtada N adet vezir için olası pozisyonlar hesaplanıyor
     *
     * @param n vezir sayısı
     * @return  çözüm sayısı
     */
    private static int Nvezircozum(int n) {
        int sol     = 0;
        int med     = n >> 1;       // n/2
        int nMask   = (1 << n) - 1; // n-long bitmask filled with 1s
        int col;

        // Her çözüm için simetrik ikinci bir çözüm var.
        // Tam ortadan yani y eksininden simetri var.
        // Yani ilk vezirin sol yarıya yerleştirerek bulunan çözümlerle sonucu hesaplamak mümkün.

        for (int y = 0; y < med; y++) {
            col = 1 << y;

            sol += (findPos(nMask, col, col >>> 1, col << 1) << 1);
        }

        // Ancak N sayısı tek ise simetrik çözümler yok demektir.
        if (1 == ((n) & 1)) {
            col = 1 << med;

            sol += findPos(nMask, col, col >>> 1, col << 1);
        }

        return sol;
    }

    /**
     * Bir sonraki vezirin konumunu hesaplamak için recursive bir fonksiyon.

     */
    private static int findPos(int nMask, int bmRow, int bmDL, int bmDR) {

        int sol = 0;
        int y, newBmRow;


        int yMask = nMask & ~(bmRow | bmDL | bmDR);

        while(yMask > 0) {
            y           = -yMask & yMask;
            newBmRow    = bmRow | y;


            yMask ^= y;

            // Daha fazla vezir yerleştirebilir miyiz onu kontrol etmek için.
            if (newBmRow < nMask) {

                sol += findPos(
                        nMask,
                        newBmRow,
                        (bmDL | y) >>> 1,
                        (bmDR | y) << 1
                );
                continue;

            } else {

                sol++;
            }
        }

        return sol;
    }
}