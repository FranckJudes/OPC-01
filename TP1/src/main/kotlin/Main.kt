import java.util.*

data class Client(val nom: String, val numeroCompteur: Int, var consommation: Double)

// Fonction pour récupérer les clients via l'entrée utilisateur
fun recupererClients(scanner: Scanner): MutableList<Client> {
    val clients = mutableListOf<Client>()

    var iteration = true
    while (iteration) {
        println("Entrer le nom du client : ")
        val nom = scanner.nextLine()

        println("Entrer le numero de compteur : ")
        val numeroCompteur = scanner.nextInt()

        println("Entrer la consommation : ")
        val consommation = scanner.nextDouble()


        clients.add(Client(nom, numeroCompteur, consommation))

        scanner.nextLine()
        println("Voulez-vous add un autre client ? (oui/non)")
        val reponse = scanner.nextLine()
        if (reponse.lowercase() != "oui") {
            iteration = false
        }
    }
    return clients
}



// Fonction pour vérifier Anormalie  calculer la facture avaec 50FCFA/KWh
fun verifierConsommationAvance(client: Client) {
    val consommation = client.consommation

    if (consommation > 5000) {
        throw ConsommationAnormaleException("Consommation anormale détectée pour ${client.nom} (>5000 kWh)")
    } else if (consommation < 0) {
        println("Erreur : Consommation negative")
    } else {
        val facture = consommation * 50 // 50 FCFA par kWh
        println("Consommation : $consommation kWh, Facture : $facture FCFA")
    }
}

fun List<Double>.moyenneConsommation(): Double {
    var final : Double = 0.0;
    if (this.isEmpty()) {
        final = 0.0;
    } else{
        this.sum() / this.size
    }
    return  final;
}

fun main() {
    println("Bienvenue à ENEO !")
        val scanner = Scanner(System.`in`)
        val clients = recupererClients(scanner)

        println("\nClients enregistrés :")
        for (client in clients) {
            println("Client : ${client.nom}, Numéro de compteur : ${client.numeroCompteur}, Consommation : ${client.consommation} kWh")
        }

    for (client in clients) {
        try {
            println("\nClient : ${client.nom}, Numéro de compteur : ${client.numeroCompteur}")
            verifierConsommationAvance(client)
        } catch (e: ConsommationAnormaleException) {
            println("Exception : ${e.message}")
        } catch (e: Exception) {
            println("Erreur inattendue : ${e.message}")
        }
    }
    val consommations = clients.map { it.consommation }
    val consommationTotale = consommations.sum()
    val consommationMoyenne = consommations.moyenneConsommation()

    print("\n")
    println("Consommation totale pour tous les client : $consommationTotale kWh")
    println("Consommation moyenne pour tous les client : $consommationMoyenne kWh")
}
// Classe d'exception personnalisée
class ConsommationAnormaleException(message: String) : Exception(message)
