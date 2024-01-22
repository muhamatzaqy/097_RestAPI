package com.example.restapi.repository


import com.example.restapi.model.Kontak
import com.example.restapi.network.KontakService
import java.io.IOException

interface KontakRepository {
    /** Fetches list of kontak from kontakApi */

    suspend fun getKontak(): List<Kontak>
    suspend fun insertKontak(kontak: Kontak)
    suspend fun updateKontak(id: Int, kontak: Kontak)
    suspend fun deleteKontak(id: Int)
    suspend fun getKontakById(id: Int): Kontak
}

class NetworkKontakRepository(
    private val kontakApiService: KontakService

) : KontakRepository{
    /** Fetches list of kontak from kontakApi */

    override suspend fun getKontak(): List<Kontak> = kontakApiService.getKontak()

    //insert kontak
    override suspend fun insertKontak(kontak: Kontak) {
        kontakApiService.insertKontak(kontak)
    }

    //update kontak
    override suspend fun updateKontak(id: Int, kontak: Kontak) {
        kontakApiService.updateKontak(id, kontak)
    }

    override suspend fun deleteKontak(id: Int) {
        try {
            val response = kontakApiService.deleteKontak(id)
            if (!response.isSuccessful){
                throw IOException("Failed to delete kontak. HTTP status code: ${response.code()}")
            }
            else{
                response.message()
            }

        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getKontakById(id: Int): Kontak {
        return kontakApiService.getKontakById(id)
    }
}