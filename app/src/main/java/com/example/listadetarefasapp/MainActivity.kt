package com.example.listadetarefasapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadetarefasapp.adapter.TarefaAdapter
import com.example.listadetarefasapp.database.TarefaDAO
import com.example.listadetarefasapp.databinding.ActivityMainBinding
import com.example.listadetarefasapp.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private  var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter : TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabAdicionar.setOnClickListener {
            val intent =  Intent(this,AdicionarTarefaActivity::class.java)
            startActivity(intent)
        }

        //RecyclerView
        tarefaAdapter = TarefaAdapter(
            {id-> confirmarExcluir(id) },
            {tarefa -> editar(tarefa) }
        )
        binding.rvTarefas.adapter = tarefaAdapter
        binding.rvTarefas.layoutManager =LinearLayoutManager(this)

    }

    private fun editar(tarefa: Tarefa) {

        val intent  = Intent (this,AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa",tarefa)
        startActivity(intent)

    }

    private fun confirmarExcluir(id: Int) {

        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle("Confirmar Exclusão")
        alertBuilder.setMessage("Deseja realmente excluir a tarefas ?")

        alertBuilder.setPositiveButton("Sim"){
            _,_,->

            val tarefaDAO = TarefaDAO(this)

            if(tarefaDAO.remover(id)){
                atualizarListaTarefas()
                Toast.makeText(this,
                    "Sucesso ao remover a tarefa",
                    Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(this,
                    "Error ao remover a tarefa",
                    Toast.LENGTH_SHORT)
                    .show()

            }
        }

        alertBuilder.setNegativeButton("Não"){
                _,_,-> }

        alertBuilder.create().show()
    }

    private fun atualizarListaTarefas(){

        val tarefaDAO = TarefaDAO(this)
        listaTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)

    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()

    }

}