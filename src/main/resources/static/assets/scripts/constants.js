const courses = [
  {
    name: "Gestão financeira",
    duration: "3",
    conclusion: "15",
    description: "Muitos problemas de uma empresa podem ser solucionados com uma boa gestão financeira. Veja neste curso como se posicionar para controlar as finanças em seu negócio.",
    url: "https://www.sebrae.com.br/sites/PortalSebrae/cursosonline/gestao-financeira,7370b8a6a28bb610VgnVCM1000004c00210aRCRD"
  },
  {
    name: "Contador Parceiro: construindo o sucesso",
    duration: "48",
    conclusion: "75",
    description: "Capacitar os profissionais da contabilidade para atuar em gestão, empreendedorismo e inovação visando a criação de uma rede de suporte e consultoria para os pequenos negócios.",
    url: "https://www.sebrae.com.br/sites/PortalSebrae/cursosonline/contador-parceiro-construindo-o-sucesso,af35681a0e0f8710VgnVCM100000d701210aRCRD"
  },
  {
    name: "Comunicação no processo de vendas para pequenos negócios",
    duration: "6",
    conclusion: "15",
    description: "O curso mostra a importância da comunicação no processo de venda e como fazê-la da forma mais adequada ao seu negócio.",
    url: "https://www.sebrae.com.br/sites/PortalSebrae/cursosonline/comunicacao-no-processo-de-vendas-para-pequenos-negocios,2b77575592143810VgnVCM100000d701210aRCRD"
  },

  {
    name: "Inovação e agilidade: o futuro do empreendedorismo",
    duration: "20",
    conclusion: "30",
    description: "A pandemia de Covid 19 acelerou de maneira imprevista a adoção de mudanças e a digitalização dos negócios, e por isso a necessidade desses conhecimentos aumentou exponencialmente.",
    url: "https://www.sebrae.com.br/sites/PortalSebrae/cursosonline/inovacao-e-agilidade-o-futuro-do-empreendedorismo,c269f07e6fb5c710VgnVCM100000d701210aRCRD"
  },

  {
    name: "Como turbinar suas vendas",
    duration: "3",
    conclusion: "15",
    description: "Veja neste curso que para vender é preciso conhecer os produtos, os clientes e as necessidades do mercado.",
    url: "https://www.sebrae.com.br/sites/PortalSebrae/cursosonline/como-turbinar-suas-vendas,1ba0b8a6a28bb610VgnVCM1000004c00210aRCRD"
  },

  {
    name: "Formação do preço de venda",
    duration: "8",
    conclusion: "30",
    description: "Aprenda a estruturar e analisar os custos de sua empresa para estipular o preço de venda adequado para os seus produtos ou serviços.",
    url: "https://www.sebrae.com.br/sites/PortalSebrae/cursosonline/formacao-do-preco-de-venda,7490b8a6a28bb610VgnVCM1000004c00210aRCRD"
  },

  {
    name: "Marketing digital para sua empresa: reforçando sua presença",
    duration: "6",
    conclusion: "15",
    description: "Saiba como impulsionar os resultados do seu negócio através do marketing digital.",
    url: "https://www.sebrae.com.br/sites/PortalSebrae/cursosonline/marketing-digital-para-sua-empresa-reforcando-sua-presenca,c0c7125576a4e710VgnVCM100000d701210aRCRD"
  },

  {
    name: "Como comprar bem e de bons fornecedores",
    duration: "2",
    conclusion: "15",
    description: "Aprenda estratégias valiosas para comprar bem, adquirir insumos de qualidade e obter preços e prazos favoráveis aos pagamentos, aumentando a lucratividade dos seus negócios.",
    url: "https://www.sebrae.com.br/sites/PortalSebrae/cursosonline/como-comprar-bem-e-de-bons-fornecedores,0101b8a6a28bb610VgnVCM1000004c00210aRCRD"
  },

  {
    name: "Gestão de sua loja virtual em marketplace",
    duration: "2",
    conclusion: "15",
    description: "Ensinará ao participante quais são os principais aspectos da gestão de uma loja virtual em marketplaces, bem como seus indicadores e ferramentas oferecidas pelas operadoras de marketplaces.",
    url: "https://www.sebrae.com.br/sites/PortalSebrae/cursosonline/como-melhorar-a-gestao-de-sua-loja-virtual-em-marketplace,0f9beb34f5193710VgnVCM1000004c00210aRCRD"
  },

]

const coursesSection = document.querySelector("#profile-study #main .section div")

courses.forEach(course => {

  const card = `<div class="card card-study">
  <h3 class="card-header">${course.name}</h3>
  <div>
    <p> <i class="bi bi-alarm"></i> Duração ${course.duration}h</p>
    <p> <i class="bi bi-calendar3"></i> Conclusão ${course.conclusion} dias</p>
  </div>
  <p class="card-body">${course.description}</p>
  <a href="${course.url}"
    class="btn" target="_blanck">Saiba mais</a>
  <form action="/cursos/salvar" method="post">
    <!-- Campos ocultos para passar informações ao controlador -->
    <input type="hidden" name="name" value="${course.name}">
    <input type="hidden" name="url"
      value="${course.url}">
    <input type="hidden" name="description"
      value="${course.description}">
      <input type="hidden" name="conclusion"
      value="${course.conclusion}">
      <input type="hidden" name="duration"
      value="${course.duration}">
    <!-- Botão para salvar o curso -->
    <button type="submit" class="btn btn-secondary" target="_blank">Adicionar aos favoritos</button>
  </form>
</div>


  `
  coursesSection.innerHTML += card
})