package co.edu.uptc.view;

import co.edu.uptc.model.State;
import co.edu.uptc.model.Transition;
import co.edu.uptc.model.DFA;
import co.edu.uptc.model.exceptions.NullException;
import co.edu.uptc.model.exceptions.ObjectAlreadyExists;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunSimulatorPanel extends JPanel {
    private ManagerView managerView;
    private JLabel runSimulatorTitle;
    private JTextField runSimulatorInput;
    private JButton runSimulatorButton;
    private JLabel stringValidate;
    private GraphPanel graphPanel;

    // Configuración visual del grafo
    private static final int STATE_RADIUS = 30;
    private static final int GRAPH_WIDTH = 600;
    private static final int GRAPH_HEIGHT = 400;
    private static final Color STATE_COLOR = Color.LIGHT_GRAY;
    private static final Color FINAL_STATE_COLOR = Color.GREEN;
    private static final Color INITIAL_STATE_COLOR = Color.YELLOW;
    private static final Color TRANSITION_COLOR = Color.BLACK;

    public RunSimulatorPanel(ManagerView managerView) {
        this.setLayout(new BorderLayout());
        this.managerView = managerView;
        initComponents();
    }

    private void initComponents() {
        // Panel superior para controles
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        createRunSimulatorTitle();
        createRunSimulatorInput();
        createRunSimulatorButton();
        createStringValidate();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        controlPanel.add(runSimulatorTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        controlPanel.add(runSimulatorInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        controlPanel.add(runSimulatorButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        controlPanel.add(stringValidate, gbc);

        // Panel para el grafo
        graphPanel = new GraphPanel();
        graphPanel.setPreferredSize(new Dimension(GRAPH_WIDTH, GRAPH_HEIGHT));
        graphPanel.setBorder(BorderFactory.createTitledBorder("Autómata Finito Determinista"));

        // Agregar componentes al panel principal
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(graphPanel, BorderLayout.CENTER);
    }

    private void createRunSimulatorTitle() {
        runSimulatorTitle = new JLabel("Ejecutar simulador");
        runSimulatorTitle.setHorizontalAlignment(SwingConstants.CENTER);
        runSimulatorTitle.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void createRunSimulatorInput() {
        runSimulatorInput = new JTextField();
        runSimulatorInput.setColumns(20);
    }

    private void createRunSimulatorButton() {
        runSimulatorButton = new JButton("Ejecutar");
        runSimulatorButton.addActionListener(e -> {
            String result = managerView.getPresenter().validate(runSimulatorInput.getText());
            System.out.println(result);
            stringValidate.setText(result);
            graphPanel.repaint(); // Actualizar el grafo
            revalidate();
        });
    }

    private void createStringValidate(){
        stringValidate = new JLabel("Sin cadena");
        stringValidate.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // Clase interna para pintar el grafo
    private class GraphPanel extends JPanel {
        private Map<State, Point> statePositions;

        public GraphPanel() {
            setBackground(Color.WHITE);
            statePositions = new HashMap<>();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Configurar renderizado suave
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2));

            DFA dfa = getDFA(); // Método para obtener el DFA del presenter
            if (dfa == null || dfa.getStates().isEmpty()) {
                drawNoAutomataMessage(g2d);
                return;
            }

            // Calcular posiciones de los estados
            calculateStatePositions(dfa.getStates());

            // Dibujar transiciones primero (para que queden detrás de los estados)
            drawTransitions(g2d, dfa.getTransitions());

            // Dibujar estados
            drawStates(g2d, dfa.getStates());
        }

        private DFA getDFA() {
            // Este método debe implementarse para obtener el DFA del presenter
            try {
                return managerView.getPresenter().getDFA();
            } catch (Exception e) {
                return null;
            }
        }

        private void drawNoAutomataMessage(Graphics2D g2d) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Arial", Font.ITALIC, 16));
            String message = "No hay autómata definido";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(message)) / 2;
            int y = getHeight() / 2;
            g2d.drawString(message, x, y);
        }

        private void calculateStatePositions(List<State> states) {
            statePositions.clear();
            int numStates = states.size();
            if (numStates == 0) return;

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            if (numStates == 1) {
                statePositions.put(states.get(0), new Point(centerX, centerY));
                return;
            }

            // Distribuir estados en círculo
            double angleStep = 2 * Math.PI / numStates;
            int radius = Math.min(getWidth(), getHeight()) / 3;

            for (int i = 0; i < numStates; i++) {
                double angle = i * angleStep - Math.PI / 2; // Empezar desde arriba
                int x = centerX + (int) (radius * Math.cos(angle));
                int y = centerY + (int) (radius * Math.sin(angle));
                statePositions.put(states.get(i), new Point(x, y));
            }
        }

        private void drawStates(Graphics2D g2d, List<State> states) {
            for (State state : states) {
                Point pos = statePositions.get(state);
                if (pos == null) continue;

                // Determinar color del estado
                Color stateColor = STATE_COLOR;
                if (state.isInitial() && state.isFinal()) {
                    stateColor = Color.ORANGE;
                } else if (state.isInitial()) {
                    stateColor = INITIAL_STATE_COLOR;
                } else if (state.isFinal()) {
                    stateColor = FINAL_STATE_COLOR;
                }

                // Dibujar círculo del estado
                g2d.setColor(stateColor);
                g2d.fillOval(pos.x - STATE_RADIUS, pos.y - STATE_RADIUS,
                        STATE_RADIUS * 2, STATE_RADIUS * 2);

                g2d.setColor(Color.BLACK);
                g2d.drawOval(pos.x - STATE_RADIUS, pos.y - STATE_RADIUS,
                        STATE_RADIUS * 2, STATE_RADIUS * 2);

                // Dibujar círculo doble para estados finales
                if (state.isFinal()) {
                    g2d.drawOval(pos.x - STATE_RADIUS + 5, pos.y - STATE_RADIUS + 5,
                            (STATE_RADIUS - 5) * 2, (STATE_RADIUS - 5) * 2);
                }

                // Dibujar flecha de entrada para estado inicial
                if (state.isInitial()) {
                    drawInitialArrow(g2d, pos);
                }

                // Dibujar nombre del estado
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                FontMetrics fm = g2d.getFontMetrics();
                String stateName = state.getName();
                int textWidth = fm.stringWidth(stateName);
                int textHeight = fm.getHeight();
                g2d.drawString(stateName, pos.x - textWidth/2, pos.y + textHeight/4);
            }
        }

        private void drawInitialArrow(Graphics2D g2d, Point statePos) {
            int arrowLength = 40;
            int startX = statePos.x - STATE_RADIUS - arrowLength;
            int startY = statePos.y;
            int endX = statePos.x - STATE_RADIUS;
            int endY = statePos.y;

            // Dibujar línea de la flecha
            g2d.drawLine(startX, startY, endX, endY);

            // Dibujar punta de la flecha
            int arrowSize = 8;
            int[] xPoints = {endX, endX - arrowSize, endX - arrowSize};
            int[] yPoints = {endY, endY - arrowSize/2, endY + arrowSize/2};
            g2d.fillPolygon(xPoints, yPoints, 3);
        }

        private void drawTransitions(Graphics2D g2d, List<Transition> transitions) {
            g2d.setColor(TRANSITION_COLOR);

            // Detectar transiciones bidireccionales
            Map<String, List<Transition>> bidirectionalMap = findBidirectionalTransitions(transitions);

            for (Transition transition : transitions) {
                Point fromPos = statePositions.get(transition.getOriginState());
                Point toPos = statePositions.get(transition.getDestinationState());

                if (fromPos == null || toPos == null) continue;

                if (transition.getOriginState().equals(transition.getDestinationState())) {
                    // Auto-transición (bucle)
                    drawSelfLoop(g2d, fromPos, transition.getSymbol());
                } else {
                    // Verificar si es bidireccional
                    String pairKey = createStatePairKey(transition.getOriginState(), transition.getDestinationState());
                    if (bidirectionalMap.containsKey(pairKey) && bidirectionalMap.get(pairKey).size() > 1) {
                        // Transición bidireccional - usar curva
                        boolean isFirstDirection = isFirstDirection(transition, bidirectionalMap.get(pairKey));
                        drawBidirectionalTransition(g2d, fromPos, toPos, transition.getSymbol(), isFirstDirection);
                    } else {
                        // Transición unidireccional normal
                        drawTransitionArrow(g2d, fromPos, toPos, transition.getSymbol());
                    }
                }
            }
        }

        private Map<String, List<Transition>> findBidirectionalTransitions(List<Transition> transitions) {
            Map<String, List<Transition>> bidirectionalMap = new HashMap<>();

            for (Transition transition : transitions) {
                if (!transition.getOriginState().equals(transition.getDestinationState())) {
                    String pairKey = createStatePairKey(transition.getOriginState(), transition.getDestinationState());
                    bidirectionalMap.computeIfAbsent(pairKey, k -> new ArrayList<>()).add(transition);
                }
            }

            return bidirectionalMap;
        }

        private String createStatePairKey(State state1, State state2) {
            // Crear una clave única para el par de estados (orden independiente)
            String name1 = state1.getName();
            String name2 = state2.getName();
            return name1.compareTo(name2) < 0 ? name1 + "-" + name2 : name2 + "-" + name1;
        }

        private boolean isFirstDirection(Transition transition, List<Transition> bidirectionalTransitions) {
            // Determinar si esta transición es la "primera" dirección para mantener consistencia
            for (Transition t : bidirectionalTransitions) {
                if (t.getOriginState().getName().compareTo(transition.getOriginState().getName()) < 0) {
                    return t.equals(transition);
                } else if (t.getOriginState().getName().equals(transition.getOriginState().getName())) {
                    return t.getDestinationState().getName().compareTo(transition.getDestinationState().getName()) <= 0 && t.equals(transition);
                }
            }
            return true;
        }

        private void drawBidirectionalTransition(Graphics2D g2d, Point from, Point to, char symbol, boolean curveUp) {
            // Calcular ángulo entre los puntos
            double angle = Math.atan2(to.y - from.y, to.x - from.x);

            // Calcular puntos en el borde de los círculos
            int startX = from.x + (int)(STATE_RADIUS * Math.cos(angle));
            int startY = from.y + (int)(STATE_RADIUS * Math.sin(angle));
            int endX = to.x - (int)(STATE_RADIUS * Math.cos(angle));
            int endY = to.y - (int)(STATE_RADIUS * Math.sin(angle));

            // Calcular punto de control para la curva
            double midX = (startX + endX) / 2.0;
            double midY = (startY + endY) / 2.0;

            // Desplazar el punto de control perpendicularmente a la línea
            double perpAngle = angle + Math.PI / 2;
            int curveOffset = curveUp ? 40 : -40; // Diferentes direcciones para cada curva

            double controlX = midX + curveOffset * Math.cos(perpAngle);
            double controlY = midY + curveOffset * Math.sin(perpAngle);

            // Crear y dibujar la curva cuadrática
            QuadCurve2D.Double curve = new QuadCurve2D.Double(startX, startY, controlX, controlY, endX, endY);
            g2d.draw(curve);

            // Dibujar punta de flecha en la curva
            drawCurvedArrowHead(g2d, curve, endX, endY);

            // Dibujar etiqueta del símbolo
            drawCurvedTransitionLabel(g2d, controlX, controlY, symbol);
        }

        private void drawCurvedArrowHead(Graphics2D g2d, QuadCurve2D.Double curve, int endX, int endY) {
            // Calcular la dirección de la tangente al final de la curva
            double t = 0.95; // Punto cerca del final de la curva para calcular la tangente

            // Calcular el punto en la curva en t=0.95
            double x1 = curve.getX1();
            double y1 = curve.getY1();
            double ctrlX = curve.getCtrlX();
            double ctrlY = curve.getCtrlY();
            double x2 = curve.getX2();
            double y2 = curve.getY2();

            double prevX = (1-t)*(1-t)*x1 + 2*(1-t)*t*ctrlX + t*t*x2;
            double prevY = (1-t)*(1-t)*y1 + 2*(1-t)*t*ctrlY + t*t*y2;

            // Calcular ángulo de la tangente
            double angle = Math.atan2(endY - prevY, endX - prevX);

            // Dibujar punta de flecha
            int arrowSize = 8;
            int x1Arrow = endX - (int)(arrowSize * Math.cos(angle - Math.PI/6));
            int y1Arrow = endY - (int)(arrowSize * Math.sin(angle - Math.PI/6));
            int x2Arrow = endX - (int)(arrowSize * Math.cos(angle + Math.PI/6));
            int y2Arrow = endY - (int)(arrowSize * Math.sin(angle + Math.PI/6));

            int[] xPoints = {endX, x1Arrow, x2Arrow};
            int[] yPoints = {endY, y1Arrow, y2Arrow};
            g2d.fillPolygon(xPoints, yPoints, 3);
        }

        private void drawCurvedTransitionLabel(Graphics2D g2d, double controlX, double controlY, char symbol) {
            g2d.setFont(new Font("Arial", Font.PLAIN, 11));

            // Crear fondo blanco para la etiqueta
            String label = String.valueOf(symbol);
            FontMetrics fm = g2d.getFontMetrics();
            int labelWidth = fm.stringWidth(label);
            int labelHeight = fm.getHeight();

            int labelX = (int)controlX - labelWidth/2;
            int labelY = (int)controlY + labelHeight/4;

            // Dibujar fondo blanco
            g2d.setColor(Color.WHITE);
            g2d.fillRect(labelX - 2, labelY - labelHeight + 2, labelWidth + 4, labelHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(labelX - 2, labelY - labelHeight + 2, labelWidth + 4, labelHeight);

            // Dibujar etiqueta
            g2d.drawString(label, labelX, labelY);
        }

        private void drawSelfLoop(Graphics2D g2d, Point statePos, char symbol) {
            int loopRadius = 20;
            int loopX = statePos.x - loopRadius;
            int loopY = statePos.y - STATE_RADIUS - loopRadius;

            g2d.drawOval(loopX, loopY, loopRadius * 2, loopRadius * 2);

            // Dibujar punta de flecha
            int arrowX = statePos.x;
            int arrowY = statePos.y - STATE_RADIUS;
            drawArrowHead(g2d, arrowX - 5, arrowY, arrowX, arrowY);

            // Dibujar etiqueta
            g2d.setFont(new Font("Arial", Font.PLAIN, 11));
            g2d.drawString(String.valueOf(symbol), statePos.x - 5, loopY - 5);
        }

        private void drawTransitionArrow(Graphics2D g2d, Point from, Point to, char symbol) {
            // Calcular puntos en el borde de los círculos
            double angle = Math.atan2(to.y - from.y, to.x - from.x);
            int startX = from.x + (int)(STATE_RADIUS * Math.cos(angle));
            int startY = from.y + (int)(STATE_RADIUS * Math.sin(angle));
            int endX = to.x - (int)(STATE_RADIUS * Math.cos(angle));
            int endY = to.y - (int)(STATE_RADIUS * Math.sin(angle));

            // Dibujar línea
            g2d.drawLine(startX, startY, endX, endY);

            // Dibujar punta de flecha
            drawArrowHead(g2d, startX, startY, endX, endY);

            // Dibujar etiqueta del símbolo
            int midX = (startX + endX) / 2;
            int midY = (startY + endY) / 2;
            g2d.setFont(new Font("Arial", Font.PLAIN, 11));
            FontMetrics fm = g2d.getFontMetrics();
            String label = String.valueOf(symbol);
            int labelWidth = fm.stringWidth(label);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(midX - labelWidth/2 - 2, midY - 8, labelWidth + 4, 16);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(midX - labelWidth/2 - 2, midY - 8, labelWidth + 4, 16);
            g2d.drawString(label, midX - labelWidth/2, midY + 3);
        }

        private void drawArrowHead(Graphics2D g2d, int startX, int startY, int endX, int endY) {
            double angle = Math.atan2(endY - startY, endX - startX);
            int arrowSize = 8;

            int x1 = endX - (int)(arrowSize * Math.cos(angle - Math.PI/6));
            int y1 = endY - (int)(arrowSize * Math.sin(angle - Math.PI/6));
            int x2 = endX - (int)(arrowSize * Math.cos(angle + Math.PI/6));
            int y2 = endY - (int)(arrowSize * Math.sin(angle + Math.PI/6));

            int[] xPoints = {endX, x1, x2};
            int[] yPoints = {endY, y1, y2};
            g2d.fillPolygon(xPoints, yPoints, 3);
        }
    }
}
