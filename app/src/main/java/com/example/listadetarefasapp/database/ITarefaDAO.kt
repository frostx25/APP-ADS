package com.example.listadetarefasapp.database

import com.example.listadetarefasapp.model.Tarefa

interface ITarefaDAO {

    fun salvar(tarefa:Tarefa):Boolean
    fun atualizar(tarefa:Tarefa):Boolean
    fun remover(idTarefa: Int):Boolean
    fun listar() : List<Tarefa>
}

