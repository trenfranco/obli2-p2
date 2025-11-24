package dominio;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Reporte {
    
    /**
     * ejemplo:
     * curl "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent" \ -H 'Content-Type: application/json' \ -H 'X-goog-api-key: AIzaSyCJDSyarLnTg41OFswBI_HqafYI_W-Zr1E' \ -X POST \ -d '{ "contents": [ { "parts": [ { "text": "Explain how AI works in a few words" } ] } ] }'
     */

    private static final String GEMINI_API_KEY = "AIzaSyD7S6L73MRhvC_epHLpmf3e97y5bzURtr8";
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    private Area areaOrigen;
    private Area areaDestino;
    private Empleado empleado;
    private String textoReporte;

    public Reporte(Area areaOrigen, Area areaDestino, Empleado empleado) {
        this.areaOrigen = areaOrigen;
        this.areaDestino = areaDestino;
        this.empleado = empleado;
        this.textoReporte = "";
    }

    
    public String generarReporteConLLM() {
        String prompt = construirPrompt();

        try {
            this.textoReporte = llamarGeminiAPI(prompt);
        } catch (Exception e) {
            System.err.println("Error al llamar a Gemini API: " + e.getMessage());
            e.printStackTrace();
            this.textoReporte = generarReporteSimulado();
        }

        return this.textoReporte;
    }

    private String llamarGeminiAPI(String prompt) throws Exception {
        URL url = new URL(GEMINI_API_URL);

        // Abrir connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-goog-api-key", GEMINI_API_KEY);
        connection.setDoOutput(true);

        // Construir el body JSON usando gson
        JsonObject requestBody = new JsonObject();
        JsonArray contents = new JsonArray();
        JsonObject content = new JsonObject();
        JsonArray parts = new JsonArray();
        JsonObject part = new JsonObject();

        part.addProperty("text", prompt);
        parts.add(part);
        content.add("parts", parts);
        contents.add(content);
        requestBody.add("contents", contents);

        // hacer request
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            throw new Exception("Error HTTP: " + responseCode);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();

        String textoRespuesta = jsonResponse
                .getAsJsonArray("candidates")
                .get(0).getAsJsonObject()
                .getAsJsonObject("content")
                .getAsJsonArray("parts")
                .get(0).getAsJsonObject()
                .get("text").getAsString();

        return formatearRespuestaLLM(textoRespuesta);
    }

    /**
     * Formatea la respuesta del LLM para presentarla mejor
     * @param respuestaLLM Texto crudo del LLM
     * @return Texto formateado con encabezado
     */
    private String formatearRespuestaLLM(String respuestaLLM) {
        StringBuilder reporte = new StringBuilder();

        reporte.append("**********************************************\n");
        reporte.append("         REPORTE INTELIGENTE DE MOVIMIENTO\n");
        reporte.append("            Generado por Gemini AI\n");
        reporte.append("**********************************************\n\n");

        reporte.append("EMPLEADO: ").append(empleado.getNombre()).append("\n");
        reporte.append("MOVIMIENTO: ").append(areaOrigen.getNombre())
               .append(" → ").append(areaDestino.getNombre()).append("\n\n");

        reporte.append("─────────────────────────────────────────────────────\n\n");
        reporte.append(respuestaLLM);
        reporte.append("\n\n(\"**********************************************\\n");

        return reporte.toString();
    }


    private String construirPrompt() {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Analiza el siguiente movimiento de empleado y genera un reporte con ventajas y desventajas porfavor:\n\n");

        prompt.append("ÁREA DE ORIGEN:\n");
        prompt.append("Nombre: ").append(areaOrigen.getNombre()).append("\n");
        prompt.append("Descripción: ").append(areaOrigen.getDescripcion()).append("\n\n");

        prompt.append("ÁREA DE DESTINO:\n");
        prompt.append("Nombre: ").append(areaDestino.getNombre()).append("\n");
        prompt.append("Descripción: ").append(areaDestino.getDescripcion()).append("\n\n");

        prompt.append("EMPLEADO:\n");
        prompt.append("Nombre: ").append(empleado.getNombre()).append("\n");
        prompt.append("Cédula: ").append(empleado.getCedula()).append("\n");
        prompt.append("Salario: USD ").append(empleado.getSalario()).append("\n");
        prompt.append("Curriculum:\n").append(empleado.getTextoCurriculum()).append("\n\n");

        prompt.append("Por favor, proporciona un análisis detallado de:\n");
        prompt.append("1. Ventajas de este movimiento\n");
        prompt.append("2. Desventajas de este movimiento\n");
        prompt.append("3. Recomendaciones\n");

        return prompt.toString();
    }


    private String generarReporteSimulado() {
        //para testear
        StringBuilder reporte = new StringBuilder();

        reporte.append("═══════════════════════════════════════════════════════\n");
        reporte.append("         REPORTE INTELIGENTE DE MOVIMIENTO\n");
        reporte.append("═══════════════════════════════════════════════════════\n\n");

        reporte.append("EMPLEADO: ").append(empleado.getNombre()).append("\n");
        reporte.append("MOVIMIENTO: ").append(areaOrigen.getNombre())
               .append(" → ").append(areaDestino.getNombre()).append("\n\n");

        reporte.append("─────────────────────────────────────────────────────\n");
        reporte.append("VENTAJAS:\n");
        reporte.append("─────────────────────────────────────────────────────\n\n");
        reporte.append("• El perfil del empleado se alinea con las necesidades\n");
        reporte.append("  del área de ").append(areaDestino.getNombre()).append(".\n\n");
        reporte.append("• La experiencia previa puede aportar nuevas perspectivas\n");
        reporte.append("  y conocimientos al equipo destino.\n\n");
        reporte.append("• Oportunidad de desarrollo profesional para el empleado.\n\n");

        reporte.append("─────────────────────────────────────────────────────\n");
        reporte.append("DESVENTAJAS:\n");
        reporte.append("─────────────────────────────────────────────────────\n\n");
        reporte.append("• El área de ").append(areaOrigen.getNombre())
               .append(" perderá un recurso valioso.\n\n");
        reporte.append("• Período de adaptación necesario en la nueva área.\n\n");
        reporte.append("• Posible impacto en proyectos actuales del área origen.\n\n");

        reporte.append("─────────────────────────────────────────────────────\n");
        reporte.append("RECOMENDACIONES:\n");
        reporte.append("─────────────────────────────────────────────────────\n\n");
        reporte.append("• Planificar un período de transición de 2-4 semanas.\n\n");
        reporte.append("• Documentar el conocimiento antes del movimiento.\n\n");
        reporte.append("• Asignar un mentor en el área destino.\n\n");

        reporte.append("═══════════════════════════════════════════════════════\n");
        reporte.append("NOTA: Este es un reporte simulado. La integración con\n");
        reporte.append("      LLM proporcionará análisis más detallados.\n");
        reporte.append("═══════════════════════════════════════════════════════\n");

        return reporte.toString();
    }

    // Getters y Setters
    public Area getAreaOrigen() {
        return areaOrigen;
    }

    public Area getAreaDestino() {
        return areaDestino;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public String getTextoReporte() {
        return textoReporte;
    }
}
