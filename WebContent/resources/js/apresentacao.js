$(document).ready(function(){
                $('#loginBlock').hide();
                $(".blockProjeto").hide();
                $(".blockObjetivo").hide();
                
                $(".blockProjeto").animate({"left": "+=900px"}, "slow");
                $(".blockObjetivo").animate({"left": "+=900px"}, "slow");
                var boolGeral = false;
                var boolProjeto = false;
                $('#menuProjeto').click(function(){
                    $(".blockProjeto").show();
                    if(boolProjeto){
                        $(".blockProjeto").animate({"left": "+=900px"}, "slow");
                        boolProjeto = false;
                    }
                    else{
                        $(".blockProjeto").animate({"left": "-=900px"}, "slow");
                        boolProjeto = true;
                    }
                });
                var boolObjetivo = false;
                $('#menuObjetivo').click(function(){
                     $(".blockObjetivo").show();
                    if(boolObjetivo){
                        $(".blockObjetivo").animate({"left": "+=900px"}, "slow");
                        boolObjetivo = false;
                    }
                    else{
                        $(".blockObjetivo").animate({"left": "-=900px"}, "slow");
                        boolObjetivo = true;
                    }
                });
                
                // funções para login e senha
                $('.login').click(function(){
                    $(this).attr("value","");
                });
                $('.login').blur(function(){
                    var strlogin = $(this).attr("value");
                    if(strlogin == 0 || strlogin == ""){
                        $(this).attr("value","Login:");
                    }
                });
                $('.senha').click(function(){
                    $(this).attr("value","");
                });
                $('.senha').blur(function(){
                    var strlogin = $(this).attr("value");
                    if(strlogin == 0 || strlogin == ""){
                        $(this).attr("value","Senha:");
                    }
                });
                var mostrando = false;
                $('#entrar').click(function(){
                    if(mostrando){
                        $('#loginBlock').fadeOut();
                        mostrando = false;
                    }
                    else{
                        $('#loginBlock').fadeIn();
                        mostrando = true;
                    }              
                });
            });