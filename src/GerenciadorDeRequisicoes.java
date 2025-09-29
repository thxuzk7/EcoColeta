import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeRequisicoes
    {
        // Lista em memória que armazena todos os pontos de coleta cadastrados.
        // A persistência é volátil (dados perdem-se ao desligar o servidor).
        private final List<PontoDeColeta> pontos;

        public GerenciadorDeRequisicoes()
            {
                pontos = new ArrayList<>();
            }

        public String[] processar(String requisicao) //Processa a requisição do cliente e retorna uma array de strings como resposta.
            {
                // Lógica para listar todos os pontos de coleta.
                if  ("listar".equals(requisicao))
                    {
                        if  (pontos.isEmpty())
                            {
                                // Retorna "null" se não houver pontos cadastrados.
                                return new String[]{"null"};
                            }
                        else
                            {
                                // Cria um array de strings para os dados + o marcador de fim.
                                String[] lista = new String[pontos.size() + 1];

                                // Serializa cada PontoDeColeta para uma string (formato "rua;numero;...").
                                for (int i = 0; i < pontos.size(); i++)
                                    {
                                        lista[i] = pontos.get(i).serializar();
                                    }

                                // Marcador de fim de transmissão. Essencial para o cliente saber quando parar de ler.
                                lista[pontos.size()] = "FIM";
                                return lista;
                            }
                    }
                // Lógica para adicionar um novo ponto de coleta.
                else if (requisicao.startsWith("adicionar"))
                    {
                        // Extrai a string de dados após o comando "adicionar".
                        String dados = requisicao.substring("adicionar".length()).trim();
                        // Divide os dados com base no separador ';'.
                        String[] partes = dados.split(";");

                        if  (partes.length >= 7)
                            {
                                ArrayList<String> materiais = new ArrayList<>();
                                // Verifica se a lista de materiais está presente (8ª parte).
                                if  (partes.length == 8)
                                    {
                                        // Processa a lista de materiais (separados por ',').
                                        for (String m : partes[7].split(","))
                                            {
                                                materiais.add(m.trim());
                                            }
                                    }

                                // Cria o novo objeto PontoDeColeta e o adiciona à lista em memória.
                                PontoDeColeta ponto = new PontoDeColeta(
                                        partes[0], partes[1], partes[2],
                                        partes[3], partes[4], partes[5], partes[6],
                                        materiais
                                );
                                pontos.add(ponto);
                            }

                        // Confirmação de sucesso para o cliente.
                        return new String[]{"success"};
                    }
                // Lógica para editar um ponto de coleta existente.
                else if(requisicao.startsWith("editar"))
                    {
                        // Extrai a string de dados.
                        String dados = requisicao.substring("editar".length());
                        // O separador ':' é usado para distinguir o ponto antigo do novo.
                        int indexDoSeparador = dados.indexOf(':');

                        // Ponto antigo (a ser substituído).
                        String ponto = dados.substring(0, indexDoSeparador);
                        // Ponto novo (dados atualizados).
                        String pontoEditado = dados.substring(indexDoSeparador + 1);

                        // Converte as strings serializadas de volta para objetos PontoDeColeta.
                        PontoDeColeta pontoAntigo = getPontoDeColeta(ponto);
                        PontoDeColeta pontoNovo = getPontoDeColeta(pontoEditado);

                        // Itera sobre a lista para encontrar e substituir o ponto.
                        for (int i = 0; i < pontos.size(); i++)
                            {
                                // Usa o metodo toString() do PontoDeColeta para comparação (chave única).
                                if  (pontos.get(i).toString().equals(pontoAntigo.toString()))
                                    {
                                        pontos.set(i, pontoNovo); // Substituição.
                                        break;
                                    }
                            }

                        return new String[]{"success"};
                    }
                // Lógica para excluir um ponto de coleta.
                else if (requisicao.startsWith("excluir"))
                    {
                        // Extrai a string serializada do ponto a ser excluído.
                        String dados = requisicao.substring("excluir".length()).trim();

                        // Itera sobre a lista para encontrar e remover o ponto.
                        for (int i = 0; i < pontos.size(); i++)
                            {
                                // Usa o metodo toString() para comparação (chave única).
                                if  (pontos.get(i).toString().equals(dados))
                                    {
                                        pontos.remove(i);
                                        break;
                                    }
                            }
                        return new String[]{"success"};
                    }

                // Resposta padrão para requisições não reconhecidas.
                return new String[]{"erro"};
            }

        private PontoDeColeta getPontoDeColeta(String ponto) //Metodo auxiliar para desserializar (converter) uma string de dados em um objeto PontoDeColeta.
            {
                String[] dadosPonto = ponto.split(";");
                ArrayList<String> materiais = new ArrayList<>();

                // Se houver 8 partes, a última é a lista de materiais.
                if  (dadosPonto.length == 8)
                    {
                        // Processa a lista de materiais (separados por ',').
                        for (String m : dadosPonto[7].split(","))
                            {
                                materiais.add(m.trim());
                            }
                    }
                // O construtor é chamado com todas as partes do endereço e a lista de materiais.
                return new PontoDeColeta(
                        dadosPonto[0], dadosPonto[1], dadosPonto[2],
                        dadosPonto[3], dadosPonto[4], dadosPonto[5],
                        dadosPonto[6], materiais
                );
            }
    }