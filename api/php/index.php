<?php
header('Content-Type: application/json');

require_once 'Database.php';
$pdo = Database::getInstance()->getConnection();

$codigo = $_GET['code'] ?? '';

$sql = "SELECT nombre, precio, stock FROM productos WHERE codigo = :codigo";
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':codigo', $codigo);
$stmt->execute();
$product = $stmt->fetch(PDO::FETCH_ASSOC);

if ($product) {
    echo json_encode([
        'exists' => true,
        'code' => $codigo,
        'name' => $product['nombre'],
        'price' => $product['precio'],
        'stock' => $product['stock']
    ]);
} else {
    echo json_encode([
        'exists' => false
    ]);
}
