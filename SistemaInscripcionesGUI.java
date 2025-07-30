import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class SistemaInscripcionesGUI {
    // Datos de usuario válido
    private static final String USUARIO_VALIDO = "admin";
    private static final String CONTRASENA_VALIDA = "admin123";

    // Estructuras para almacenar datos
    private static ArrayList<Curso> cursos = new ArrayList<>();
    private static ArrayList<Estudiante> estudiantes = new ArrayList<>();
    private static ArrayList<Inscripcion> inscripciones = new ArrayList<>();

    // Componentes de la interfaz
    private static JFrame frame;
    private static JPanel currentPanel;

    public static void main(String[] args) {
        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicializar datos
        inicializarDatos();

        // Crear y mostrar la ventana de login
        mostrarVentanaLogin();

        // Crear ventana principal (inicialmente invisible)
        crearVentanaPrincipal();
    }

    private static void inicializarDatos() {
        // Agregar algunos cursos de prueba
        cursos.add(new Curso(1, "Natación", "Aprende a nadar", 20));
        cursos.add(new Curso(2, "Pintura", "Arte y creatividad", 15));
        cursos.add(new Curso(3, "Programación", "Introducción a Java", 10));

        // Agregar algunos estudiantes de prueba
        estudiantes.add(new Estudiante(1, "Juan Pérez", "juan@email.com", "555-1234"));
        estudiantes.add(new Estudiante(2, "María García", "maria@email.com", "555-5678"));
    }

    private static void mostrarVentanaLogin() {
        JFrame loginFrame = new JFrame("Inicio de Sesión");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(350, 200);
        loginFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Componentes
        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField(15);
        JLabel lblContrasena = new JLabel("Contraseña:");
        JPasswordField txtContrasena = new JPasswordField(15);
        JButton btnLogin = new JButton("Iniciar Sesión");

        // Posicionamiento
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsuario, gbc);

        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblContrasena, gbc);

        gbc.gridx = 1;
        panel.add(txtContrasena, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        // Acción del botón
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());

                if (usuario.equals(USUARIO_VALIDO) && contrasena.equals(CONTRASENA_VALIDA)) {
                    loginFrame.dispose();
                    frame.setVisible(true);
                    mostrarPanelPrincipal();
                } else {
                    JOptionPane.showMessageDialog(loginFrame,
                            "Usuario o contraseña incorrectos",
                            "Error de autenticación",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    private static void crearVentanaPrincipal() {
        frame = new JFrame("Sistema de Inscripciones - Cursos Vacacionales");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Menú superior
        JMenuBar menuBar = new JMenuBar();

        JMenu menuOpciones = new JMenu("Opciones");
        JMenuItem itemCursos = new JMenuItem("Gestión de Cursos");
        JMenuItem itemEstudiantes = new JMenuItem("Gestión de Estudiantes");
        JMenuItem itemInscripciones = new JMenuItem("Gestión de Inscripciones");
        JMenuItem itemSalir = new JMenuItem("Salir");

        menuOpciones.add(itemCursos);
        menuOpciones.add(itemEstudiantes);
        menuOpciones.add(itemInscripciones);
        menuOpciones.addSeparator();
        menuOpciones.add(itemSalir);

        menuBar.add(menuOpciones);
        frame.setJMenuBar(menuBar);

        // Listeners del menú
        itemCursos.addActionListener(e -> mostrarPanelCursos());
        itemEstudiantes.addActionListener(e -> mostrarPanelEstudiantes());
        itemInscripciones.addActionListener(e -> mostrarPanelInscripciones());
        itemSalir.addActionListener(e -> System.exit(0));

        // Panel inicial
        currentPanel = new JPanel();
        frame.add(currentPanel);
    }

    private static void mostrarPanelPrincipal() {
        cambiarPanel(new PanelBienvenida());
    }

    private static void mostrarPanelCursos() {
        cambiarPanel(new PanelCursos());
    }

    private static void mostrarPanelEstudiantes() {
        cambiarPanel(new PanelEstudiantes());
    }

    private static void mostrarPanelInscripciones() {
        cambiarPanel(new PanelInscripciones());
    }

    private static void cambiarPanel(JPanel nuevoPanel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(nuevoPanel);
        frame.revalidate();
        frame.repaint();
        currentPanel = nuevoPanel;
    }

    // Clases para los diferentes paneles
    static class PanelBienvenida extends JPanel {
        public PanelBienvenida() {
            setLayout(new BorderLayout());

            JLabel lblTitulo = new JLabel("Bienvenido al Sistema de Inscripciones", SwingConstants.CENTER);
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

            JLabel lblSubtitulo = new JLabel("Seleccione una opción del menú superior", SwingConstants.CENTER);
            lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));

            add(lblTitulo, BorderLayout.CENTER);
            add(lblSubtitulo, BorderLayout.SOUTH);
        }
    }

    static class PanelCursos extends JPanel {
        private JTable tabla;

        public PanelCursos() {
            setLayout(new BorderLayout());

            // Botones superiores
            JPanel panelBotones = new JPanel();
            JButton btnAgregar = new JButton("Agregar Curso");
            JButton btnEditar = new JButton("Editar Curso");
            JButton btnEliminar = new JButton("Eliminar Curso");
            JButton btnActualizar = new JButton("Actualizar Lista");
            JButton btnVolver = new JButton("Volver al Inicio");

            panelBotones.add(btnAgregar);
            panelBotones.add(btnEditar);
            panelBotones.add(btnEliminar);
            panelBotones.add(btnActualizar);
            panelBotones.add(btnVolver);

            // Tabla de cursos
            String[] columnas = {"ID", "Nombre", "Descripción", "Cupo Máximo"};
            Object[][] datos = new Object[cursos.size()][4];

            for (int i = 0; i < cursos.size(); i++) {
                Curso curso = cursos.get(i);
                datos[i][0] = curso.getId();
                datos[i][1] = curso.getNombre();
                datos[i][2] = curso.getDescripcion();
                datos[i][3] = curso.getCupoMaximo();
            }

            tabla = new JTable(datos, columnas);
            tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(tabla);

            // Acciones de los botones
            btnAgregar.addActionListener(e -> mostrarDialogoAgregarCurso());
            btnEditar.addActionListener(e -> mostrarDialogoEditarCurso());
            btnEliminar.addActionListener(e -> eliminarCurso());
            btnActualizar.addActionListener(e -> mostrarPanelCursos());
            btnVolver.addActionListener(e -> mostrarPanelPrincipal());

            add(panelBotones, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
        }

        private void mostrarDialogoAgregarCurso() {
            JDialog dialog = new JDialog(frame, "Agregar Nuevo Curso", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(frame);

            JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

            JLabel lblId = new JLabel("ID:");
            JTextField txtId = new JTextField();
            JLabel lblNombre = new JLabel("Nombre:");
            JTextField txtNombre = new JTextField();
            JLabel lblDescripcion = new JLabel("Descripción:");
            JTextField txtDescripcion = new JTextField();
            JLabel lblCupo = new JLabel("Cupo Máximo:");
            JTextField txtCupo = new JTextField();

            JButton btnCancelar = new JButton("Cancelar");
            JButton btnGuardar = new JButton("Guardar");

            panel.add(lblId);
            panel.add(txtId);
            panel.add(lblNombre);
            panel.add(txtNombre);
            panel.add(lblDescripcion);
            panel.add(txtDescripcion);
            panel.add(lblCupo);
            panel.add(txtCupo);
            panel.add(btnCancelar);
            panel.add(btnGuardar);

            btnCancelar.addActionListener(event -> dialog.dispose());
            btnGuardar.addActionListener(event -> {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    String nombre = txtNombre.getText();
                    String descripcion = txtDescripcion.getText();
                    int cupo = Integer.parseInt(txtCupo.getText());

                    // Validar ID único
                    boolean idExiste = cursos.stream().anyMatch(c -> c.getId() == id);
                    if (idExiste) {
                        JOptionPane.showMessageDialog(dialog,
                                "El ID ya existe. Por favor, use un ID único.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    cursos.add(new Curso(id, nombre, descripcion, cupo));
                    JOptionPane.showMessageDialog(dialog, "Curso agregado exitosamente!");
                    dialog.dispose();
                    mostrarPanelCursos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Por favor ingrese valores numéricos válidos para ID y Cupo",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(panel);
            dialog.setVisible(true);
        }

        private void mostrarDialogoEditarCurso() {
            int selectedRow = tabla.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Por favor seleccione un curso para editar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cursoId = (int) tabla.getValueAt(selectedRow, 0);
            Curso curso = cursos.stream().filter(c -> c.getId() == cursoId).findFirst().orElse(null);
            if (curso == null) return;

            JDialog dialog = new JDialog(frame, "Editar Curso", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(frame);

            JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

            JLabel lblId = new JLabel("ID (no editable):");
            JTextField txtId = new JTextField(String.valueOf(curso.getId()));
            txtId.setEditable(false);
            JLabel lblNombre = new JLabel("Nombre:");
            JTextField txtNombre = new JTextField(curso.getNombre());
            JLabel lblDescripcion = new JLabel("Descripción:");
            JTextField txtDescripcion = new JTextField(curso.getDescripcion());
            JLabel lblCupo = new JLabel("Cupo Máximo:");
            JTextField txtCupo = new JTextField(String.valueOf(curso.getCupoMaximo()));

            JButton btnCancelar = new JButton("Cancelar");
            JButton btnGuardar = new JButton("Guardar");

            panel.add(lblId);
            panel.add(txtId);
            panel.add(lblNombre);
            panel.add(txtNombre);
            panel.add(lblDescripcion);
            panel.add(txtDescripcion);
            panel.add(lblCupo);
            panel.add(txtCupo);
            panel.add(btnCancelar);
            panel.add(btnGuardar);

            btnCancelar.addActionListener(event -> dialog.dispose());
            btnGuardar.addActionListener(event -> {
                try {
                    String nombre = txtNombre.getText();
                    String descripcion = txtDescripcion.getText();
                    int cupo = Integer.parseInt(txtCupo.getText());

                    curso.setNombre(nombre);
                    curso.setDescripcion(descripcion);
                    curso.setCupoMaximo(cupo);

                    JOptionPane.showMessageDialog(dialog, "Curso actualizado exitosamente!");
                    dialog.dispose();
                    mostrarPanelCursos();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Por favor ingrese un valor numérico válido para Cupo",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(panel);
            dialog.setVisible(true);
        }

        private void eliminarCurso() {
            int selectedRow = tabla.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Por favor seleccione un curso para eliminar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cursoId = (int) tabla.getValueAt(selectedRow, 0);
            Curso curso = cursos.stream().filter(c -> c.getId() == cursoId).findFirst().orElse(null);
            if (curso == null) return;

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar el curso " + curso.getNombre() + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                cursos.remove(curso);
                JOptionPane.showMessageDialog(this, "Curso eliminado exitosamente!");
                mostrarPanelCursos();
            }
        }
    }

    static class PanelEstudiantes extends JPanel {
        private JTable tabla;

        public PanelEstudiantes() {
            setLayout(new BorderLayout());

            // Botones superiores
            JPanel panelBotones = new JPanel();
            JButton btnAgregar = new JButton("Agregar Estudiante");
            JButton btnEditar = new JButton("Editar Estudiante");
            JButton btnEliminar = new JButton("Eliminar Estudiante");
            JButton btnActualizar = new JButton("Actualizar Lista");
            JButton btnVolver = new JButton("Volver al Inicio");

            panelBotones.add(btnAgregar);
            panelBotones.add(btnEditar);
            panelBotones.add(btnEliminar);
            panelBotones.add(btnActualizar);
            panelBotones.add(btnVolver);

            // Tabla de estudiantes
            String[] columnas = {"ID", "Nombre", "Email", "Teléfono"};
            Object[][] datos = new Object[estudiantes.size()][4];

            for (int i = 0; i < estudiantes.size(); i++) {
                Estudiante estudiante = estudiantes.get(i);
                datos[i][0] = estudiante.getId();
                datos[i][1] = estudiante.getNombre();
                datos[i][2] = estudiante.getEmail();
                datos[i][3] = estudiante.getTelefono();
            }

            tabla = new JTable(datos, columnas);
            tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(tabla);

            // Acciones de los botones
            btnAgregar.addActionListener(e -> mostrarDialogoAgregarEstudiante());
            btnEditar.addActionListener(e -> mostrarDialogoEditarEstudiante());
            btnEliminar.addActionListener(e -> eliminarEstudiante());
            btnActualizar.addActionListener(e -> mostrarPanelEstudiantes());
            btnVolver.addActionListener(e -> mostrarPanelPrincipal());

            add(panelBotones, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
        }

        private void mostrarDialogoAgregarEstudiante() {
            JDialog dialog = new JDialog(frame, "Agregar Nuevo Estudiante", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(frame);

            JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

            JLabel lblId = new JLabel("ID:");
            JTextField txtId = new JTextField();
            JLabel lblNombre = new JLabel("Nombre:");
            JTextField txtNombre = new JTextField();
            JLabel lblEmail = new JLabel("Email:");
            JTextField txtEmail = new JTextField();
            JLabel lblTelefono = new JLabel("Teléfono:");
            JTextField txtTelefono = new JTextField();

            JButton btnCancelar = new JButton("Cancelar");
            JButton btnGuardar = new JButton("Guardar");

            panel.add(lblId);
            panel.add(txtId);
            panel.add(lblNombre);
            panel.add(txtNombre);
            panel.add(lblEmail);
            panel.add(txtEmail);
            panel.add(lblTelefono);
            panel.add(txtTelefono);
            panel.add(btnCancelar);
            panel.add(btnGuardar);

            btnCancelar.addActionListener(eventCancel -> dialog.dispose());
            btnGuardar.addActionListener(eventSave -> {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    String nombre = txtNombre.getText();
                    String email = txtEmail.getText();
                    String telefono = txtTelefono.getText();

                    // Validar ID único
                    boolean idExiste = estudiantes.stream().anyMatch(e -> e.getId() == id);
                    if (idExiste) {
                        JOptionPane.showMessageDialog(dialog,
                                "El ID ya existe. Por favor, use un ID único.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    estudiantes.add(new Estudiante(id, nombre, email, telefono));
                    JOptionPane.showMessageDialog(dialog, "Estudiante agregado exitosamente!");
                    dialog.dispose();
                    mostrarPanelEstudiantes();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Por favor ingrese un ID numérico válido",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(panel);
            dialog.setVisible(true);
        }

        private void mostrarDialogoEditarEstudiante() {
            int selectedRow = tabla.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Por favor seleccione un estudiante para editar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int estudianteId = (int) tabla.getValueAt(selectedRow, 0);
            Estudiante estudiante = estudiantes.stream().filter(e -> e.getId() == estudianteId).findFirst().orElse(null);
            if (estudiante == null) return;

            JDialog dialog = new JDialog(frame, "Editar Estudiante", true);
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(frame);

            JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

            JLabel lblId = new JLabel("ID (no editable):");
            JTextField txtId = new JTextField(String.valueOf(estudiante.getId()));
            txtId.setEditable(false);
            JLabel lblNombre = new JLabel("Nombre:");
            JTextField txtNombre = new JTextField(estudiante.getNombre());
            JLabel lblEmail = new JLabel("Email:");
            JTextField txtEmail = new JTextField(estudiante.getEmail());
            JLabel lblTelefono = new JLabel("Teléfono:");
            JTextField txtTelefono = new JTextField(estudiante.getTelefono());

            JButton btnCancelar = new JButton("Cancelar");
            JButton btnGuardar = new JButton("Guardar");

            panel.add(lblId);
            panel.add(txtId);
            panel.add(lblNombre);
            panel.add(txtNombre);
            panel.add(lblEmail);
            panel.add(txtEmail);
            panel.add(lblTelefono);
            panel.add(txtTelefono);
            panel.add(btnCancelar);
            panel.add(btnGuardar);

            btnCancelar.addActionListener(eventCancel -> dialog.dispose());
            btnGuardar.addActionListener(eventSave -> {
                try {
                    String nombre = txtNombre.getText();
                    String email = txtEmail.getText();
                    String telefono = txtTelefono.getText();

                    estudiante.setNombre(nombre);
                    estudiante.setEmail(email);
                    estudiante.setTelefono(telefono);

                    JOptionPane.showMessageDialog(dialog, "Estudiante actualizado exitosamente!");
                    dialog.dispose();
                    mostrarPanelEstudiantes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Error al actualizar el estudiante",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(panel);
            dialog.setVisible(true);
        }

        private void eliminarEstudiante() {
            int selectedRow = tabla.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Por favor seleccione un estudiante para eliminar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int estudianteId = (int) tabla.getValueAt(selectedRow, 0);
            Estudiante estudiante = estudiantes.stream().filter(e -> e.getId() == estudianteId).findFirst().orElse(null);
            if (estudiante == null) return;

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar al estudiante " + estudiante.getNombre() + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                estudiantes.remove(estudiante);
                JOptionPane.showMessageDialog(this, "Estudiante eliminado exitosamente!");
                mostrarPanelEstudiantes();
            }
        }
    }

    static class PanelInscripciones extends JPanel {
        private JTable tabla;

        public PanelInscripciones() {
            setLayout(new BorderLayout());

            // Botones superiores
            JPanel panelBotones = new JPanel();
            JButton btnNueva = new JButton("Nueva Inscripción");
            JButton btnEditar = new JButton("Editar Inscripción");
            JButton btnEliminar = new JButton("Eliminar Inscripción");
            JButton btnActualizar = new JButton("Actualizar Lista");
            JButton btnCambiarEstado = new JButton("Cambiar Estado");
            JButton btnVolver = new JButton("Volver al Inicio");

            panelBotones.add(btnNueva);
            panelBotones.add(btnEditar);
            panelBotones.add(btnEliminar);
            panelBotones.add(btnActualizar);
            panelBotones.add(btnCambiarEstado);
            panelBotones.add(btnVolver);

            // Tabla de inscripciones
            String[] columnas = {"ID", "Fecha", "Estado", "Estudiante", "Curso"};
            Object[][] datos = new Object[inscripciones.size()][5];

            for (int i = 0; i < inscripciones.size(); i++) {
                Inscripcion inscripcion = inscripciones.get(i);
                datos[i][0] = inscripcion.getId();
                datos[i][1] = inscripcion.getFecha().toString();
                datos[i][2] = inscripcion.getEstado();
                datos[i][3] = inscripcion.getEstudiante().getNombre();
                datos[i][4] = inscripcion.getCurso().getNombre();
            }

            tabla = new JTable(datos, columnas);
            tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(tabla);

            // Acciones de los botones
            btnNueva.addActionListener(e -> mostrarDialogoNuevaInscripcion());
            btnEditar.addActionListener(e -> mostrarDialogoEditarInscripcion());
            btnEliminar.addActionListener(e -> eliminarInscripcion());
            btnActualizar.addActionListener(e -> mostrarPanelInscripciones());
            btnCambiarEstado.addActionListener(e -> mostrarDialogoCambiarEstado());
            btnVolver.addActionListener(e -> mostrarPanelPrincipal());

            add(panelBotones, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
        }

        private void mostrarDialogoNuevaInscripcion() {
            JDialog dialog = new JDialog(frame, "Nueva Inscripción", true);
            dialog.setSize(500, 300);
            dialog.setLocationRelativeTo(frame);

            JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

            // Combo box para estudiantes
            JLabel lblEstudiante = new JLabel("Estudiante:");
            JComboBox<Estudiante> cbEstudiantes = new JComboBox<>();
            for (Estudiante e : estudiantes) {
                cbEstudiantes.addItem(e);
            }

            // Combo box para cursos
            JLabel lblCurso = new JLabel("Curso:");
            JComboBox<Curso> cbCursos = new JComboBox<>();
            for (Curso c : cursos) {
                cbCursos.addItem(c);
            }

            JLabel lblEstado = new JLabel("Estado inicial:");
            JComboBox<String> cbEstado = new JComboBox<>(new String[]{"Activa", "Cancelada", "Completada"});

            JButton btnCancelar = new JButton("Cancelar");
            JButton btnGuardar = new JButton("Guardar");

            panel.add(lblEstudiante);
            panel.add(cbEstudiantes);
            panel.add(lblCurso);
            panel.add(cbCursos);
            panel.add(lblEstado);
            panel.add(cbEstado);
            panel.add(btnCancelar);
            panel.add(btnGuardar);

            btnCancelar.addActionListener(eventCancel -> dialog.dispose());
            btnGuardar.addActionListener(eventSave -> {
                Estudiante estudiante = (Estudiante) cbEstudiantes.getSelectedItem();
                Curso curso = (Curso) cbCursos.getSelectedItem();
                String estado = (String) cbEstado.getSelectedItem();

                Inscripcion nueva = new Inscripcion(
                        inscripciones.size() + 1,
                        new Date(),
                        estado,
                        estudiante,
                        curso
                );

                inscripciones.add(nueva);
                JOptionPane.showMessageDialog(dialog, "Inscripción creada exitosamente!");
                dialog.dispose();
                mostrarPanelInscripciones();
            });

            dialog.add(panel);
            dialog.setVisible(true);
        }

        private void mostrarDialogoEditarInscripcion() {
            int selectedRow = tabla.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Por favor seleccione una inscripción para editar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int inscripcionId = (int) tabla.getValueAt(selectedRow, 0);
            Inscripcion inscripcion = inscripciones.stream().filter(i -> i.getId() == inscripcionId).findFirst().orElse(null);
            if (inscripcion == null) return;

            JDialog dialog = new JDialog(frame, "Editar Inscripción", true);
            dialog.setSize(500, 300);
            dialog.setLocationRelativeTo(frame);

            JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

            // Combo box para estudiantes
            JLabel lblEstudiante = new JLabel("Estudiante:");
            JComboBox<Estudiante> cbEstudiantes = new JComboBox<>();
            for (Estudiante e : estudiantes) {
                cbEstudiantes.addItem(e);
            }
            cbEstudiantes.setSelectedItem(inscripcion.getEstudiante());

            // Combo box para cursos
            JLabel lblCurso = new JLabel("Curso:");
            JComboBox<Curso> cbCursos = new JComboBox<>();
            for (Curso c : cursos) {
                cbCursos.addItem(c);
            }
            cbCursos.setSelectedItem(inscripcion.getCurso());

            JLabel lblEstado = new JLabel("Estado:");
            JComboBox<String> cbEstado = new JComboBox<>(new String[]{"Activa", "Cancelada", "Completada"});
            cbEstado.setSelectedItem(inscripcion.getEstado());

            JButton btnCancelar = new JButton("Cancelar");
            JButton btnGuardar = new JButton("Guardar");

            panel.add(lblEstudiante);
            panel.add(cbEstudiantes);
            panel.add(lblCurso);
            panel.add(cbCursos);
            panel.add(lblEstado);
            panel.add(cbEstado);
            panel.add(btnCancelar);
            panel.add(btnGuardar);

            btnCancelar.addActionListener(eventCancel -> dialog.dispose());
            btnGuardar.addActionListener(eventSave -> {
                Estudiante estudiante = (Estudiante) cbEstudiantes.getSelectedItem();
                Curso curso = (Curso) cbCursos.getSelectedItem();
                String estado = (String) cbEstado.getSelectedItem();

                inscripcion.setEstudiante(estudiante);
                inscripcion.setCurso(curso);
                inscripcion.setEstado(estado);

                JOptionPane.showMessageDialog(dialog, "Inscripción actualizada exitosamente!");
                dialog.dispose();
                mostrarPanelInscripciones();
            });

            dialog.add(panel);
            dialog.setVisible(true);
        }

        private void mostrarDialogoCambiarEstado() {
            if (inscripciones.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay inscripciones registradas",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog(frame, "Cambiar Estado de Inscripción", true);
            dialog.setSize(400, 200);
            dialog.setLocationRelativeTo(frame);

            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

            // Combo box para inscripciones
            JLabel lblInscripcion = new JLabel("Inscripción:");
            JComboBox<Inscripcion> cbInscripciones = new JComboBox<>();
            for (Inscripcion i : inscripciones) {
                cbInscripciones.addItem(i);
            }

            // Combo box para estados
            JLabel lblEstado = new JLabel("Nuevo estado:");
            JComboBox<String> cbEstado = new JComboBox<>(new String[]{"Activa", "Cancelada", "Completada"});

            JButton btnCancelar = new JButton("Cancelar");
            JButton btnGuardar = new JButton("Guardar");

            panel.add(lblInscripcion);
            panel.add(cbInscripciones);
            panel.add(lblEstado);
            panel.add(cbEstado);
            panel.add(btnCancelar);
            panel.add(btnGuardar);

            btnCancelar.addActionListener(eventCancel -> dialog.dispose());
            btnGuardar.addActionListener(eventSave -> {
                Inscripcion inscripcion = (Inscripcion) cbInscripciones.getSelectedItem();
                String estado = (String) cbEstado.getSelectedItem();

                inscripcion.setEstado(estado);
                JOptionPane.showMessageDialog(dialog, "Estado actualizado exitosamente!");
                dialog.dispose();
                mostrarPanelInscripciones();
            });

            dialog.add(panel);
            dialog.setVisible(true);
        }

        private void eliminarInscripcion() {
            int selectedRow = tabla.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Por favor seleccione una inscripción para eliminar",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int inscripcionId = (int) tabla.getValueAt(selectedRow, 0);
            Inscripcion inscripcion = inscripciones.stream().filter(i -> i.getId() == inscripcionId).findFirst().orElse(null);
            if (inscripcion == null) return;

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar la inscripción #" + inscripcion.getId() + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                inscripciones.remove(inscripcion);
                JOptionPane.showMessageDialog(this, "Inscripción eliminada exitosamente!");
                mostrarPanelInscripciones();
            }
        }
    }

    // Clases de modelo
    static class Curso {
        private int id;
        private String nombre;
        private String descripcion;
        private int cupoMaximo;

        public Curso(int id, String nombre, String descripcion, int cupoMaximo) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.cupoMaximo = cupoMaximo;
        }

        @Override
        public String toString() {
            return nombre;
        }

        // Getters
        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public String getDescripcion() { return descripcion; }
        public int getCupoMaximo() { return cupoMaximo; }

        // Setters para edición
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }
    }

    static class Estudiante {
        private int id;
        private String nombre;
        private String email;
        private String telefono;

        public Estudiante(int id, String nombre, String email, String telefono) {
            this.id = id;
            this.nombre = nombre;
            this.email = email;
            this.telefono = telefono;
        }

        @Override
        public String toString() {
            return nombre;
        }

        // Getters
        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public String getEmail() { return email; }
        public String getTelefono() { return telefono; }

        // Setters para edición
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setEmail(String email) { this.email = email; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
    }

    static class Inscripcion {
        private int id;
        private Date fecha;
        private String estado;
        private Estudiante estudiante;
        private Curso curso;

        public Inscripcion(int id, Date fecha, String estado, Estudiante estudiante, Curso curso) {
            this.id = id;
            this.fecha = fecha;
            this.estado = estado;
            this.estudiante = estudiante;
            this.curso = curso;
        }

        @Override
        public String toString() {
            return "Inscripción #" + id;
        }

        // Getters y Setters
        public int getId() { return id; }
        public Date getFecha() { return fecha; }
        public String getEstado() { return estado; }
        public Estudiante getEstudiante() { return estudiante; }
        public Curso getCurso() { return curso; }
        public void setEstado(String estado) { this.estado = estado; }
        public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
        public void setCurso(Curso curso) { this.curso = curso; }
    }}
