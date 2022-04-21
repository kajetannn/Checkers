public enum RodzajPiona {
    BIALY(1),INNY(-1),PUSTY(0), NIEAKTYWNY(100);

    final int kierunekRuchu;

    RodzajPiona(int kierunekRuchu){
        this.kierunekRuchu = kierunekRuchu;
    }
}
