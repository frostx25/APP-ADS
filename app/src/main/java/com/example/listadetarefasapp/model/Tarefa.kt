package com.example.listadetarefasapp.model

import java.io.Serializable

class Tarefa(
    val idTarefa : Int  ,
    val descricao : String ,
    val dataCadastro : String
) : Serializable