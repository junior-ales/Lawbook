import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String stringDeConexao = "jdbc:postgresql://72.14.185.211:5432/lawbook";
		String usuario = "lawbook";
		String senha = "";


		try {
			System.out.println("Abrindo conexao...");
			Connection conexao = DriverManager.getConnection(stringDeConexao,
					usuario, senha);

			String textoDoComando = "SELECT * FROM teste;";

			PreparedStatement comando = conexao.prepareStatement(textoDoComando);

			System.out.println("Executando comando...");
			ResultSet resultado = comando.executeQuery();

			System.out.println("Resultados encontrados: \n");
			while (resultado.next()) {
				System.out.println(resultado.getString("nome"));
			}

			System.out.println("\nFechando conexao...");
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
