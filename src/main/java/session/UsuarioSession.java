package session;

public class UsuarioSession {
    private static String usuarioId;

    public static String getUsuarioId() {
        return usuarioId;
    }

    public static void setUsuarioId(String id) {
        usuarioId = id;
    }
}
