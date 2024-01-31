const data = document.querySelector("#f-currency")



data.textContent = Number(data.textContent).toLocaleString('pt-BR', {
  style: 'currency',
  currency: 'BRL',
  minimumFractionDigits: 2,
  maximumFractionDigits: 2,
})

console.log("olaa")