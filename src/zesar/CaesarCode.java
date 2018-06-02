package zesar;

public class CaesarCode {

    private char [] symbols;
    private String [] result = new String [31];
    private char [] alphabet = new char [32], zesarAlphabet = new char [32];

    public CaesarCode(String text){
        this.symbols = text.toLowerCase().toCharArray();
    }

    public String [] doAnalysis(){
        for(int i = 0; i<31; i++)
            result[i] = "";

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
                    zesarAlphabet[i] = alphabet[i+j];
                }catch(Exception e){
                    for(int k = i, n = 0; k<32; k++, n++)
                        zesarAlphabet[k] = alphabet[n];
                    break;
                }
            }

            for(int i = 0; i<symbols.length; i++){
                for(int k = 0; k<32; k++)
                    if(symbols[i] == alphabet[k])
                        result[j-1] += zesarAlphabet[k];
            }
        }
        return result;
    }
}
