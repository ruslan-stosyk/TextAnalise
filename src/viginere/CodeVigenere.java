package viginere;

public class CodeVigenere {

    private String result;
    private char [] text, key, alphabet = new char [32];
    private char [][] tabulaRecta = new char [32][32];

    public CodeVigenere(String text, String key) {
        this.text = text.toLowerCase().toCharArray();
        if(!key.equals(""))
            this.key = key.toLowerCase().toCharArray();
        else {
            this.key = new char[1];
            this.key[0] = 'a';
        }

        result = "";

        for(int i = 0; i<26; i++){
            alphabet[i] = (char)((int)'a' + i);
        }

        alphabet[26] = (char)32;
        alphabet[27] = (char)46;
        alphabet[28] = (char)44;
        alphabet[29] = (char)59;
        alphabet[30] = (char)45;
        alphabet[31] = (char)39;

        for(int j = 1; j<32; j++){

            for(int i = 0; i<32; i++){
                try{
                    tabulaRecta[j][i] = alphabet[i+j];
                }catch(Exception e){
                    for(int k = i, n = 0; k<32; k++, n++)
                        tabulaRecta[j][k] = alphabet[n];
                    break;
                }
            }

        }

        for(int i = 0; i<32; i++)
            tabulaRecta[0][i] = alphabet[i];
    }

    public String code(){

        int a = 0;

        for(int i = 0, keyNum = 0; i<text.length; i++, keyNum++){
            if(text[i] == '\n'){
                text[i] = ' ';
            }

            for(int k = 0; k<32; k++)
                try{
                    if(key[keyNum] == alphabet[k]){
                        a = k; break;
                    }
                } catch (Exception e){
                    keyNum = 0;
                    if(key[keyNum] == alphabet[k]){
                        a = k; break;
                    }
                }


            for(int k = 0; k<32; k++){
                if(text[i] == alphabet[k])
                    result += tabulaRecta[a][k];
            }

        }
        return result;
    }

    public String decode(){

        int a = 0;
        for(int i = 0, keyNum = 0; i<text.length; i++, keyNum++){

            if(text[i] == '\n'){
                text[i] = ' ';
            }
            for(int k = 0; k<32; k++)
                try{
                    if(key[keyNum] == alphabet[k]){
                        a = k; break;
                    }
                } catch (Exception e){
                    keyNum = 0;
                    if(key[keyNum] == alphabet[k]){
                        a = k; break;
                    }
                }


            for(int k = 0; k<32; k++){
                if(text[i] == '\n')
                    break;
                if(text[i] == tabulaRecta[a][k])
                    result += alphabet[k];
            }

        }

        return result;
    }
}