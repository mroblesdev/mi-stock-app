<?php

class Database
{
    private static $instance = null;
    private $connection = null;
    private string $host = 'localhost';
    private string $database = 'mi-stock';
    private string $username = 'root';
    private string $password = '';
    private string $charset = "utf8mb4";

    public function __construct()
    {
        $this->connect();
    }

    /**
     * Evita clonación
     */
    private function __clone() {}

    /**
     * Evita deserialización
     */
    public function __wakeup()
    {
        throw new RuntimeException("No se puede deserializar un Singleton");
    }

    /**
     * Retorna la única instancia de la clase
     */
    public static function getInstance(): Database
    {
        if (self::$instance === null) {
            self::$instance = new self();
        }

        return self::$instance;
    }

    /**
     * Conecta a la base de datos
     */
    private function connect(): void
    {
        if ($this->connection !== null) {
            return;
        }

        $dsn = "mysql:host={$this->host};dbname={$this->database};charset={$this->charset}";

        $options = [
            PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
            PDO::ATTR_EMULATE_PREPARES   => false,
        ];

        try {
            $this->connection = new PDO($dsn, $this->username, $this->password, $options);
        } catch (PDOException $e) {
            throw new RuntimeException(
                "Error de conexión a la base de datos: " . $e->getMessage()
            );
        }
    }

    /**
     * Retorna la conexión PDO
     */
    public function getConnection(): PDO
    {
        return $this->connection;
    }

    /**
     * Cierra la conexión
     */
    public function close(): void
    {
        $this->connection = null;
        self::$instance = null;
    }
}
