package com.example.data

data class Guide(
    val id: String,
    val title: String,
    val difficulty: String, // "🟢 Fácil" / "🟡 Moderado" / "🔴 Difícil"
    val time: String? = null, // "⏱ 20 min" etc.
    val description: String,
    val steps: List<String>,
    val warningNotes: String? = null,
    val tipNotes: String? = null,
    val householdSources: List<String>? = null, // "🏠 ¿Dónde conseguirlo en casa?"
    val illustrationAsset: String? = null // nombre del SVG en res/drawable/guides/
)

data class Category(
    val id: String,
    val name: String,
    val emoji: String,
    val iconName: String,
    val guides: List<Guide>
)

data class ContactoDeEmergencia(
    val category: String, // "EMERGENCIAS GENERALES" / "SALUD" / "DESASTRES" / "SERVICIOS"
    val name: String,
    val number: String,
    val description: String
)

data class KitItem(
    val id: String,
    val section: String, // "AGUA Y PURIFICACIÓN" / "ILUMINACIÓN" etc.
    val name: String,
    val estimatedCost: Int
)

object SurvivalData {
    val emergencyContacts = listOf(
        // EMERGENCIAS GENERALES
        ContactoDeEmergencia("EMERGENCIAS GENERALES", "🚨 Emergencias", "911", "Policía y Emergencias Generales"),
        ContactoDeEmergencia("EMERGENCIAS GENERALES", "🏥 SAME (Buenos Aires)", "107", "Servicio de Emergencias Médicas de la Ciudad"),
        ContactoDeEmergencia("EMERGENCIAS GENERALES", "🚒 Bomberos", "100", "Bomberos y Rescate"),
        ContactoDeEmergencia("EMERGENCIAS GENERALES", "👮 Policía", "101", "Policía de la Provincia / Federal"),
        
        // SALUD
        ContactoDeEmergencia("SALUD", "🩺 SAMU (Interior)", "107", "Servicio de Atención Médica de Emergencia provincial"),
        ContactoDeEmergencia("SALUD", "☎️ Centro Antivenenos", "(011) 4923-6666", "Hospital de Niños Ricardo Gutiérrez - Emergencias Toxicológicas"),
        ContactoDeEmergencia("SALUD", "🆘 Cruz Roja Argentina", "(011) 4309-0800", "Asistencia humanitaria y primeros auxilios nacionales"),
        
        // DESASTRES
        ContactoDeEmergencia("DESASTRES", "🌊 Defensa Civil", "103", "Emergencias Climáticas, Inundaciones e Infraestructura"),
        ContactoDeEmergencia("DESASTRES", "⛈️ Alerta Meteorológica", "(011) 5167-6767", "Servicio Meteorológico Nacional - Consultas y Avisos"),
        
        // SERVICIOS
        ContactoDeEmergencia("SERVICIOS", "💧 AySA", "0800-321-2482", "Servicio de Agua y Cloacas (Buenos Aires y GBA)"),
        ContactoDeEmergencia("SERVICIOS", "⚡ Edenor", "0800-666-4366", "Reporte de Cortes de Luz y Emergencias de Red (Norte)"),
        ContactoDeEmergencia("SERVICIOS", "⚡ Edesur", "0800-333-3787", "Reporte de Cortes de Luz y Emergencias de Red (Sur)")
    )

    val kitItems = listOf(
        // AGUA Y PURIFICACIÓN
        KitItem("kit_agua_1", "AGUA Y PURIFICACIÓN", "Bidones de agua 20L x3", 15000),
        KitItem("kit_agua_2", "AGUA Y PURIFICACIÓN", "Lavandina sin perfume 1L x2", 2000),
        KitItem("kit_agua_3", "AGUA Y PURIFICACIÓN", "Pastillas potabilizadoras", 3000),
        KitItem("kit_agua_4", "AGUA Y PURIFICACIÓN", "Filtro casero (materiales botella)", 0),
        
        // ILUMINACIÓN
        KitItem("kit_ilum_1", "ILUMINACIÓN", "Velas x20", 5000),
        KitItem("kit_ilum_2", "ILUMINACIÓN", "Linterna LED + pilas extra", 8000),
        KitItem("kit_ilum_3", "ILUMINACIÓN", "Linterna de cabeza", 6000),
        KitItem("kit_ilum_4", "ILUMINACIÓN", "Fósforos x5 cajas en bolsa hermética", 2000),
        KitItem("kit_ilum_5", "ILUMINACIÓN", "Encendedor a piedra x2", 2000),
        
        // COMUNICACIÓN
        KitItem("kit_com_1", "COMUNICACIÓN", "Radio AM/FM a pilas", 15000),
        KitItem("kit_com_2", "COMUNICACIÓN", "Pilas AA x8, AAA x8", 8000),
        KitItem("kit_com_3", "COMUNICACIÓN", "Auriculares con cable para radio del celular", 3000),
        
        // PRIMEROS AUXILIOS
        KitItem("kit_aux_1", "PRIMEROS AUXILIOS", "Gasa estéril x10", 3000),
        KitItem("kit_aux_2", "PRIMEROS AUXILIOS", "Venda elástica x4", 4000),
        KitItem("kit_aux_3", "PRIMEROS AUXILIOS", "Cinta adhesiva médica", 2000),
        KitItem("kit_aux_4", "PRIMEROS AUXILIOS", "Alcohol en gel 500ml", 3000),
        KitItem("kit_aux_5", "PRIMEROS AUXILIOS", "Ibuprofeno 400mg x20 comp.", 5000),
        KitItem("kit_aux_6", "PRIMEROS AUXILIOS", "Paracetamol 500mg x20 comp.", 4000),
        KitItem("kit_aux_7", "PRIMEROS AUXILIOS", "Suero oral comercial x4", 6000),
        KitItem("kit_aux_8", "PRIMEROS AUXILIOS", "Guantes de látex x10 pares", 3000),
        
        // HERRAMIENTAS
        KitItem("kit_herr_1", "HERRAMIENTAS", "Navaja multiusos", 15000),
        KitItem("kit_herr_2", "HERRAMIENTAS", "Cuerda de 10 metros", 5000),
        KitItem("kit_herr_3", "HERRAMIENTAS", "Cinta de embalaje ancha x2 rollos", 4000),
        KitItem("kit_herr_4", "HERRAMIENTAS", "Bolsas de basura grandes x20", 3000),
        
        // ALIMENTOS
        KitItem("kit_ali_1", "ALIMENTOS (para 30 días, 1 persona)", "Arroz 5kg", 8000),
        KitItem("kit_ali_2", "ALIMENTOS (para 30 días, 1 persona)", "Fideos 3kg", 6000),
        KitItem("kit_ali_3", "ALIMENTOS (para 30 días, 1 persona)", "Lentejas 2kg", 4000),
        KitItem("kit_ali_4", "ALIMENTOS (para 30 días, 1 persona)", "Atún en lata x12", 12000),
        KitItem("kit_ali_5", "ALIMENTOS (para 30 días, 1 persona)", "Aceite 2L", 8000),
        KitItem("kit_ali_6", "ALIMENTOS (para 30 días, 1 persona)", "Sal 1kg", 1500),
        KitItem("kit_ali_7", "ALIMENTOS (para 30 días, 1 persona)", "Azúcar 2kg", 4000),
        KitItem("kit_ali_8", "ALIMENTOS (para 30 días, 1 persona)", "Miel 500g", 8000)
    )

    val categories = listOf(
        Category(
            id = "energia",
            name = "⚡ Energía de Emergencia",
            emoji = "⚡",
            iconName = "FlashOn",
            guides = listOf(
                Guide(
                    id = "energia_1",
                    illustrationAsset = "il_motor_juguete",
                    title = "Cargar el celular con un motor de juguete",
                    difficulty = "🟡 Moderado",
                    time = "⏱ 20 min",
                    description = "Generá electricidad de emergencia reciclando motores de juguetes eléctricos.",
                    steps = listOf(
                        "Buscá un motor DC de auto, tren o helicóptero de juguete eléctrico (los que usan pilas AA)",
                        "Identificá los dos cables del motor saliendo de la carcasa (generalmente rojo y negro)",
                        "Conseguí un cable USB viejo y cortalo: vas a usar solo el extremo sin el conector del celular",
                        "Pelá 2 cm de cada cable del USB: encontrarás 4 cables internos (rojo=+5V, negro=tierra, los otros son datos)",
                        "Conectá el cable rojo del motor al cable rojo del USB con cinta aislante o retorciendo",
                        "Conectá el cable negro del motor al cable negro del USB de la misma forma",
                        "Conectá el USB al celular y comenzá a girar el motor manualmente lo más rápido posible",
                        "A mayor velocidad de giro = más voltaje generado = más carga",
                        "Para estabilizar la corriente y no dañar el celular: conectá un capacitor electrolítico de 1000uF 16V en paralelo entre los cables (se consigue en cualquier casa de electrónica por menos de $500)"
                    ),
                    warningNotes = "Sin el capacitor, la corriente irregular puede ser inestable. Girá de forma constante y pareja.",
                    tipNotes = "Con una manivela improvisada (un alambre doblado) podés girar más tiempo sin cansarte. Este método genera poca energía pero es suficiente para llamadas de emergencia.",
                    householdSources = listOf(
                        "🚗 Motor DC: juguetes a pilas rotos (autos, trenes, helicópteros de radiocontrol)",
                        "📱 Cable USB: cualquier cable de carga viejo que tengas en un cajón",
                        "🔧 Cinta aislante: cajón de herramientas o ferretería del barrio",
                        "〰️ Alambre para manivela: percha de ropa metálica doblada"
                    )
                ),
                Guide(
                    id = "energia_2",
                    title = "Panel solar casero básico",
                    difficulty = "🔴 Difícil",
                    time = "⏱ 2-3 horas",
                    description = "Construí un cargador solar con celdas fotovoltaicas accesibles.",
                    steps = listOf(
                        "Conseguí entre 4 y 6 celdas solares pequeñas (en tiendas de electrónica o mercadolibre, cuestan entre $2000-$5000 cada una)",
                        "Cada celda genera aproximadamente 0.5V: necesitás conectarlas en serie para sumar voltaje",
                        "Conectar en serie: el polo positivo de una celda va al polo negativo de la siguiente (como pilas)",
                        "Soldá los cables con estaño o retorcelos bien y cubrí con cinta aislante resistente al calor",
                        "El conjunto de 6 celdas en serie genera aproximadamente 3V: necesitás un regulador TP4056 (cuesta $500-$1000) para estabilizar a 5V seguros para el celular",
                        "Conectá la salida del TP4056 a un puerto USB hembra",
                        "Encerrá todo en una caja transparente (vidrio o acrílico) fijando las celdas con silicona",
                        "En Argentina: orientá el panel mirando al NORTE para máxima exposición solar durante el día",
                        "Inclinación ideal: igual a tu latitud en grados (Buenos Aires = 34° de inclinación)"
                    ),
                    warningNotes = "Verificá la polaridad antes de conectar al celular. Polaridad invertida puede dañar el dispositivo.",
                    tipNotes = "Un panel de 6 celdas tarda entre 4-8 horas en cargar un celular moderno bajo sol directo. Es suficiente para mantenerlo funcional durante días.",
                    householdSources = listOf(
                        "☀️ Celdas solares: tiendas de electrónica, MercadoLibre o juguetes solares viejos desarmados",
                        "📦 Caja contenedora: vieja caja de zapatos rígida o marco de madera sobrante",
                        "🔌 Puerto USB: cable USB cortado o cargador de auto viejo",
                        "🔧 Silicona: ferretería, o pegamento epoxi como alternativa"
                    )
                ),
                Guide(
                    id = "energia_3",
                    title = "Cargador con dinamo de bicicleta",
                    difficulty = "🟡 Moderado",
                    time = "⏱ 1 hora de instalación",
                    description = "Convertí tu bicicleta en una estación de carga portátil.",
                    steps = listOf(
                        "Conseguí un dinamo de bicicleta de botella (el que se apoya contra el costado de la rueda, cuesta $2000-$4000 en bicicletería)",
                        "Montalo en la horquilla delantera de forma que la ruedita toque el neumático al activarlo",
                        "El dinamo genera corriente alterna (AC) de 6V: necesitás convertirla en continua (DC)",
                        "Armá un puente rectificador con 4 diodos 1N4007 (cuestan $100 cada uno en casa de electrónica)",
                        "Conectá la salida del rectificador a un regulador de voltaje LM7805 o al módulo TP4056",
                        "Desde la salida regulada conectá un puerto USB hembra",
                        "Montá el puerto USB en el manubrio con cinta o sujeta-cables para acceso fácil",
                        "Al pedalear a velocidad moderada (15-20 km/h) generás suficiente energía para cargar",
                        "10 minutos de pedaleo activo ≈ 5-8% de carga en un celular moderno"
                    ),
                    warningNotes = "El dinamo genera más voltaje a mayor velocidad. No pedalees a máxima velocidad sin el regulador: podría dañar el celular.",
                    tipNotes = "Este sistema es ideal para viajes de emergencia. Cargás mientras te desplazás hacia un punto seguro."
                ),
                Guide(
                    id = "energia_4",
                    title = "Usar batería de auto para dispositivos",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 5 min",
                    description = "Una batería de auto puede alimentar docenas de dispositivos durante días.",
                    steps = listOf(
                        "Una batería de auto estándar de 12V/45Ah puede cargar un celular moderno unas 80-100 veces",
                        "Método más simple: conseguí un cargador de auto USB de 12V (enchufe de mechero, cuesta $1500-$3000)",
                        "Conectá el cargador directo a los bornes de la batería con cables caimán (rojo al positivo, negro al negativo)",
                        "Enchufá tu cable de celular al USB y cargá normalmente",
                        "Método alternativo: usá un inversor de 12V a 220V (cuesta $5000-$15000) para enchufar cargadores normales",
                        "Una batería de auto cargada al 100% puede alimentar una lámpara LED de 10W durante 45 horas",
                        "Para saber el estado de la batería: voltaje en reposo de 12.6V = cargada, 11.8V = descargada",
                        "Guardá la batería en lugar fresco y seco, revisá que los bornes no tengan sulfatación (polvo blanco)"
                    ),
                    warningNotes = "Las baterías de auto emiten hidrógeno al cargarse. Nunca las cargues en espacios cerrados sin ventilación. Evitá chispas cerca.",
                    tipNotes = "Si tenés auto, dejá el motor encendido 20 minutos cada 2 días para mantener la batería cargada durante un apagón prolongado."
                ),
                Guide(
                    id = "energia_5",
                    title = "Ahorrar batería del celular al máximo",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 2 min",
                    description = "Extendé la duración de tu batería de 1 día a 5-7 días con estos ajustes.",
                    steps = listOf(
                        "Activá MODO AVIÓN inmediatamente: es el ahorro más grande, hasta 40% de consumo menos",
                        "Bajá el brillo de pantalla al 10-15%: la pantalla consume entre 30-40% de la batería",
                        "Desactivá: WiFi, Bluetooth, GPS, NFC, datos móviles (todo desde el panel de notificaciones)",
                        "Activá modo oscuro en toda la interfaz: en pantallas AMOLED ahorra hasta 20% adicional",
                        "Cerrá todas las apps en segundo plano: mantenés el botón de inicio y cerrás todo",
                        "Desactivá vibraciones y reducí el volumen del sonido al mínimo necesario",
                        "Usá la pantalla solo lo indispensable: consultá esta app, luego apagá la pantalla",
                        "Activá el modo de ahorro de batería del sistema (Ajustes > Batería > Ahorro de energía)",
                        "Con todas estas medidas activas, un celular con 50% de batería puede durar 4-6 días en standby"
                    ),
                    tipNotes = "En modo avión podés igual usar esta app, la linterna, la cámara y escuchar música descargada. Solo perdés llamadas y datos."
                ),
                Guide(
                    id = "energia_6",
                    title = "Lámpara de emergencia con aceite",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 5 min",
                    description = "Iluminación de emergencia que dura horas con aceite de cocina común.",
                    steps = listOf(
                        "Usá cualquier recipiente pequeño: tarro de vidrio, lata de conservas, taza de metal",
                        "Llenalo hasta la mitad con aceite de cocina vegetal (girasol, maíz, oliva, cualquiera sirve)",
                        "Cortá una tira de tela de algodón de 1 cm de ancho y 10 cm de largo (remera vieja, trapo limpio)",
                        "Enrollá la tira formando una mecha compacta",
                        "Sumergí la mecha completamente en el aceite por 2 minutos para que absorba bien",
                        "Sacá la mecha dejando 1-1.5 cm fuera del recipiente, apoyada en el borde o sujeta con alambre",
                        "Encendé el extremo que sobresale con encendedor o fósforo",
                        "La llama tardará 30 segundos en estabilizarse mientras el aceite sube por la mecha",
                        "Una lámpara de este tipo con 100ml de aceite puede durar 3-5 horas continuas"
                    ),
                    warningNotes = "Nunca dejés la lámpara encendida sin supervisión. Mantené lejos de telas, cortinas y materiales inflamables. Tené agua cerca.",
                    tipNotes = "Para más luz usá un recipiente más ancho con varias mechas. El aceite de oliva huele mejor al quemarse que el de girasol."
                ),
                Guide(
                    id = "energia_7",
                    title = "Calefactor de emergencia con velas y macetas",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 10 min",
                    description = "Calefaccioná una habitación pequeña sin gas ni electricidad.",
                    steps = listOf(
                        "Conseguí 2-4 velas largas y 2 macetas de terracota: una grande y una más chica que entre adentro",
                        "Colocá las velas encendidas sobre una superficie ignífuga (plato de metal, azulejo, bandeja)",
                        "Poné la maceta más chica invertida sobre las velas (la base hacia arriba)",
                        "Poné la maceta grande invertida encima de la chica, cubriendo todo",
                        "El agujero de drenaje de las macetas actúa como salida de calor concentrado",
                        "Las macetas de terracota antes descritas absorben el calor de las velas y lo irradian lentamente",
                        "Una habitación de 10m² puede subir 3-5°C con este sistema en 30-60 minutos",
                        "Ventilá la habitación de forma breve cada hora para renovar el oxígeno"
                    ),
                    warningNotes = "Las velas consumen oxígeno. Nunca uses este sistema en habitaciones completamente selladas sin ventilación mínima. Riesgo de intoxicación por CO2.",
                    tipNotes = "Este sistema es más efectivo de noche cuando el exterior está más frío. Cerrá doors y ventanas para retener el calor."
                )
            )
        ),
        Category(
            id = "agua",
            name = "💧 Agua y Purificación",
            emoji = "💧",
            iconName = "WaterDrop",
            guides = listOf(
                Guide(
                    id = "agua_1",
                    title = "Purificar agua con lavandina",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 35 min",
                    description = "La lavandina doméstica purifica el agua eliminando bacterias y virus.",
                    steps = listOf(
                        "Usá SOLO lavandina doméstica sin perfume, sin color y sin limpiadores agregados (fijate que diga hipoclorito de sodio al 2-6% en la etiqueta)",
                        "Para agua de aspecto visual CLARA: agregá 2 gotas de lavandina por cada litro de agua",
                        "Para agua TURBIA o con color: primero filtrá con una tela limpia, luego agregá 4 gotas por litro",
                        "Mezclá bien agitando el recipiente tapado durante 30 segundos",
                        "Esperá exactamente 30 minutos sin tapar el recipiente antes de tomar",
                        "Después de los 30 minutos: el agua debe tener un leve olor a cloro. Eso significa que funcionó",
                        "Si NO tiene olor a cloro: el agua poseía demasiada carga orgánica. Agregá 2 gotas más y esperá 15 minutos adicionales",
                        "Si el olor a cloro es excesivamente fuerte: dejá reposar destapada 15 minutos más antes de tomar"
                    ),
                    warningNotes = "Este método NO elimina metales pesados, pesticidas ni contaminantes químicos. Solo bacterias y virus. Si sospechás contaminación química, no uses esa fuente.",
                    tipNotes = "Guardá siempre al menos 2 botellas de lavandina en tu stock de emergencia. Una botella de 1 litro puede purificar hasta 10.000 litros de agua."
                ),
                Guide(
                    id = "agua_2",
                    illustrationAsset = "il_filtro_agua",
                    title = "Filtro casero de emergencia",
                    difficulty = "🟡 Moderado",
                    time = "⏱ 30 min construcción",
                    description = "Construí un filtro que quita sedimentos, olores y mejora la calidad del agua.",
                    steps = listOf(
                        "Conseguí una botella plástica de 1.5 o 2 litros y cortala exactamente por la mitad",
                        "Invertí la mitad superior (queda como un embudo) dentro de la mitad inferior que actúa como recipiente",
                        "Primera capa (abajo del embudo): colocá una tela densa doblada en 4 capas (tela de jean, lona, algodón grueso)",
                        "Segunda capa: agregá 8-10 cm de carbón vegetal triturado. IMPORTANTE: usá carbón de madera quemada, no carbón de parrilla con químicos",
                        "Cómo hacer carbón apto: quemá madera seca hasta que quede negra, apagá con agua y triturá cuando esté frío",
                        "Tercera capa: agregá 8-10 cm de arena fina limpia (lavala bien antes con agua limpia)",
                        "Cuarta capa (arriba): otra tela doblada en 4 capas para atrapar partículas grandes del agua",
                        "Verté el agua sucia lentamente por arriba y recogé el filtrado abajo",
                        "Las primeras 2-3 pasadas descartarlas: el filtro necesita lavarse a sí mismo",
                        "SIEMPRE combiná el filtrado con lavandina o hervor: el filtro mejora el agua pero no la purifica biológicamente al 100%"
                    ),
                    warningNotes = "Un filtro sucio puede contaminar el agua en lugar de purificarla. Reemplazá las capas cada 2-3 días de uso continuo.",
                    tipNotes = "Podés hacer varios filtros en serie para mejor resultado. Pasá el agua por uno y luego por otro."
                ),
                Guide(
                    id = "agua_3",
                    title = "Hervir agua correctamente",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 10 min",
                    description = "El método más confiable para eliminar patógenos del agua.",
                    steps = listOf(
                        "Filtrá primero si el agua tiene sedimentos visibles (con tela limpia)",
                        "Colocá el agua en una olla limpia y llevá a fuego",
                        "Esperá a que hierva FUERTE: burbujeo intenso y constante (no solo vaporcito)",
                        "La altura sobre el nivel del mar afecta el punto de ebullición. Se recomienda:\n- Nivel del mar a 1000m: 1 minuto es suficiente\n- 1000m a 2000m: 3 minutos\n- Más de 2000m: 5 minutos mínimo",
                        "Retirá del fuego y dejá enfriar TAPADO para evitar recontaminación",
                        "Una vez frío, trasvasá a recipientes limpios y tapados",
                        "El agua hervida suele saber 'plana': agitala o pasala de un recipiente a otro para reoxigenarla"
                    ),
                    warningNotes = "El hervor NO elimina metales pesados, nitratos ni productos químicos. Tampoco funciona si el agua tiene combustible o solventes.",
                    tipNotes = "Hervir es el método más seguro y no requiere insumos especiales. Priorizalo siempre que tengas combustible disponible."
                ),
                Guide(
                    id = "agua_4",
                    title = "Recolectar agua de lluvia de forma segura",
                    difficulty = "🟢 Fácil",
                    time = "⏱ Variable",
                    description = "Aprovechá la caída del agua pluvial aplicando medidas preventivas de sedimentos de techos.",
                    steps = listOf(
                        "Preparate ANTES de la lluvia: limpiá y posicioná recipientes grandes en un lugar despejado y abierto",
                        "Los primeros 5-10 minutos de lluvia lavan contaminantes del aire y techos: DESCARTÁ esa primera agua",
                        "A partir del minuto 10 de lluvia continua, el agua es considerablemente más limpia",
                        "Superficies aptas para recolección: lonas plásticas limpias, techos de chapa sin pintura vieja",
                        "Superficies a EVITAR: techos con tejas de asbesto, superficies pintadas con pintura vieja (puede contener plomo), techos con mucho polvo o excremento de aves",
                        "Conectá canaletas hacia baldes o tanques si podés",
                        "Cubrí los recipientes una vez que los llenaste para evitar que ingresen insectos u hojas",
                        "Siempre purificá el agua de lluvia recolectada antes de consumir (lavandina o hervor)"
                    ),
                    tipNotes = "Una lona de 2x2 metros recolecta aproximadamente 4 litros por cada 1mm de lluvia. Una lluvia moderada de 10mm te da 40 litros: suficiente para varios días."
                ),
                Guide(
                    id = "agua_5",
                    illustrationAsset = "il_destilacion_solar",
                    title = "Destilación solar casera",
                    difficulty = "🟡 Moderado",
                    time = "⏱ 4-6 horas (proceso)",
                    description = "Producí agua destilada potable incluso de agua salada o muy contaminada.",
                    steps = listOf(
                        "Conseguí un recipiente grande (balde, tina) y uno pequeño que entre adentro (taza, bol)",
                        "Poné el recipiente grande al sol y llenalo con agua contaminada o salada",
                        "Colocá el recipiente pequeño flotando o apoyado en el centro (no debe llenarse con el agua sucia)",
                        "Cubrí todo con una bolsa plástica transparente o film transparente, sellada en los bordes",
                        "Poné un peso pequeño (piedra, moneda) en el centro del plástico justo encima del recipiente chico",
                        "El sol calienta el agua: el vapor sube, condensa en el plástico frío y gotea hacia el recipiente chico",
                        "Después de 4-6 horas de sol directo, el recipiente chico tendrá agua destilada pura",
                        "Producción aproximada: 0.5 a 1 litro por día con buena exposición solar"
                    ),
                    warningNotes = "La destilación solar es lenta pero muy efectiva. Funciona incluso con agua de mar, agua con químicos o agua muy sucia.",
                    tipNotes = "Este es el único método que purifica agua con metales pesados o combustibles. Guardalo como última opción cuando no tenés lavandina ni combustible para hervir."
                )
            )
        ),
        Category(
            id = "primeros_auxilios",
            name = "🏥 Primeros Auxilios",
            emoji = "🏥",
            iconName = "LocalHospital",
            guides = listOf(
                Guide(
                    id = "aux_1",
                    illustrationAsset = "il_rcp_adulto",
                    title = "RCP completo para adultos",
                    difficulty = "🔴 Difícil",
                    time = "⏱ Continuo hasta ayuda",
                    description = "La reanimación cardiopulmonar puede salvar una vida mientras llega la ayuda.",
                    steps = listOf(
                        "EVALUÁ LA ESCENA: aseguresé de que no haya peligro para vos antes de acercarte",
                        "VERIFICÁ RESPUESTA: llamá fuerte a la persona por su nombre y golpeá sus hombros firmemente",
                        "Si no responde: PEDÍ AYUDA A GRITOS. Indicá a alguien específico: 'Vos, llamá al 107 ahora'",
                        "POSICIÓN: acostá a la persona boca arriba sobre superficie dura y plana (piso, no cama)",
                        "APERTURA DE VÍA AÉREA: incliná la cabeza hacia atrás suavemente con una mano en la frente y dos dedos bajo el mentón",
                        "VERIFICÁ RESPIRACIÓN: mirá si el pecho se mueve, escuchá y sentí aire en tu mejilla. Máximo 10 segundos",
                        "Si NO respira normalmente: comenzá compresiones torácicas INMEDIATAMENTE",
                        "COMPRESIONES: poné el talón de tu mano dominante en el CENTRO del pecho (entre los pezones). Otra mano encima, dedos entrelazados",
                        "Brazos RECTOS, usá el peso de tu cuerpo: comprimí 5-6 cm hacia abajo a ritmo de 100-120 por minuto (ritmo de la canción 'Stayin\' Alive')",
                        "Contá en voz alta: 1-2-3-4-5...hasta 30 compresiones",
                        "RESPIRACIONES DE RESCATE: incliná la cabeza, tapá la nariz, sellá tu boca sobre la suya y soplá hasta ver que el pecho se eleva. Dar 2 respiraciones.",
                        "REPETÍ el ciclo: 30 compresiones + 2 respiraciones sin parar hasta que llegue ayuda o la persona respire por sí misma"
                    ),
                    warningNotes = "Si no sabés dar respiraciones o tenés miedo, hacé SOLO compresiones sin parar. Las compresiones continuas son más efectivas que hacer mal las respiraciones.",
                    tipNotes = "El RCP cansa mucho. Si hay otra persona, turnense cada 2 minutos para mantener la calidad de las compresiones.",
                    householdSources = listOf(
                        "⏱️ Ritmo de compresiones: tarareá 'Stayin Alive' de Bee Gees (100-120 BPM exactos)",
                        "🧤 Protección: bolsa plástica fina con agujero como barrera improvisada para respiraciones de rescate",
                        "📱 Ayuda de memoria: esta guía con la pantalla en máximo brillo apoyada al lado"
                    )
                ),
                Guide(
                    id = "aux_2",
                    title = "RCP para bebés y niños",
                    difficulty = "🔴 Difícil",
                    time = "⏱ Continuo hasta ayuda",
                    description = "El RCP en menores tiene diferencias críticas respecto al de adultos.",
                    steps = listOf(
                        "BEBÉS (menos de 1 año): usá solo 2 dedos (índice y mayor) en el centro del pecho",
                        "Comprimí solo 4 cm de profundidad en bebés",
                        "Para NIÑOS (1 a 8 años): usá el talón de UNA sola mano, comprimí 5 cm",
                        "Para NIÑOS MAYORES de 8 años: igual que adultos con dos manos",
                        "Ritmo igual que adultos: 100-120 compresiones por minuto",
                        "Ciclo básico para niños: 30 compresiones + 2 respiraciones (igual que adultos)",
                        "Para bebés: cubrí nariz Y boca con tu boca al dar respiraciones, soplá suavemente",
                        "El soplo correcto en bebés: solo el volumen de tus mejillas infladas, NO de los pulmones",
                        "Verificá siempre que el pecho de la criatura se eleve levemente con cada respiración",
                        "Si hay un objeto atascado visible en la garganta de un bebé: sacalo con el dedo meñique en forma de gancho"
                    ),
                    warningNotes = "Nunca sacudas a un bebé. Nunca comprimas el estómago. Las compresiones deben ser firmes pero controladas."
                ),
                Guide(
                    id = "aux_3",
                    illustrationAsset = "il_heimlich",
                    title = "Atragantamiento - Maniobra de Heimlich",
                    difficulty = "🟡 Moderado",
                    time = "⏱ Inmediato",
                    description = "Desobstruí la vía aérea de una persona que se está ahogando con un objeto.",
                    steps = listOf(
                        "IDENTIFICÁ: la persona no puede hablar, tose sin producir sonido, o hace gestos llevando las manos al cuello/garganta",
                        "Si puede toser: animala a que siga tosiendo activamente, no intervengas todavía",
                        "Si NO puede toser ni respirar: actuá de inmediato de la siguiente forma",
                        "Posicionáte DETRÁS de la persona que se atraganta (estando de pie)",
                        "Rodeala con tus brazos a la altura de su cintura",
                        "Cerrá el puño de tu mano dominante y colocalo entre el ombligo y el esternón (centro del abdomen superior)",
                        "Agarrá tu puño cerrado firmemente con la otra mano",
                        "Realizá compresiones hacia ADENTRO y hacia ARRIBA de forma rápida y con fuerza",
                        "Repetí hasta que el objeto salga despedido o la persona pierda el conocimiento",
                        "Si pierde el conocimiento: acostala y comenzá RCP. Al abrir la vía aérea revisá si el objeto se movió"
                    ),
                    warningNotes = "Para embarazadas y personas obesas: hacé las compresiones en la base del pecho (igual que en RCP) no en el abdomen superior.",
                    tipNotes = "Si estás SOLO y te atragantás: usá el respaldo de un asiento/silla o el borde firme de una mesa para darte las compresiones abdominales vos mismo."
                ),
                Guide(
                    id = "aux_4",
                    title = "Hemorragias y vendas improvisadas",
                    difficulty = "🟡 Moderado",
                    time = "⏱ Inmediato",
                    description = "Aprendé a detener un sangrado profuso mediante presión y vendajes improvisados.",
                    steps = listOf(
                        "PRESIÓN DIRECTA es lo primero y más importante: presioná FUERTE sobre la herida con la tela limpia que tengas disponible",
                        "No levantes la tela para 'ver si ya paró': si la tela se empapa, poné más capas encima y seguí presionando",
                        "Presioná por lo menos MÍNIMO 10 minutos seguidos para cortes normales, 20+ minutos para heridas más profundas",
                        "Telas aptas para improvisar vendas (por prioridad): gasa médica, tela de algodón limpia, remeras cortadas en tiras, sábanas",
                        "Para fijar la venda: usá cinta adhesiva, vendas elásticas, tiras de ropa anudadas o alfileres de gancho",
                        "TORNIQUETE (solo si peligra la vida por sangrado extremo y no responde a presión directa):\n- Usá una tira de tela de al menos 5 cm de ancho (nunca cables ni sogas finas)\n- Colocaloguía 5-7 cm POR ENCIMA de la herida\n- Apretá con un palo girándolo hasta que pare el sangrado\n- Anotá la hora exacta del torniquete en la frente o piel del paciente",
                        "Un torniquete no debe mantenerse más de 2 horas continuas sin aflojar",
                        "Elevá el miembro herido por encima del nivel del corazón siempre que sea físicamente posible"
                    ),
                    warningNotes = "Un torniquete mal aplicado puede provocar amputación o daño irreparable. Usalo SOLO en hemorragias críticas que amenacen la vida."
                ),
                Guide(
                    id = "aux_5",
                    title = "Quemaduras - tratamiento correcto",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 20 min mínimo",
                    description = "Cómo aliviar y detener el daño tisular de las quemaduras térmicas de forma inmediata.",
                    steps = listOf(
                        "RETIRÁ la fuente de calor: apartá a la víctima, apagá la ropa que arda haciéndola rodar por el suelo",
                        "ENFRIÁ con agua fría de la canilla (corriente, no estancada) durante 20 minutos completos. Esto frena la destrucción celular.",
                        "NO uses: hielo directo, pastas dentales, cremas, manteca, aceite, alcohol, ni remedios caseros extraños",
                        "Quemaduras de 1er grado (enrojecimiento): agua fría 20 min, luego cubrí con un paño limpio húmedo",
                        "Quemaduras de 2do grado (ampollas): enfriá con agua fría, no rompas NUNCA las ampollas, cubrí con gasa/tela seca",
                        "Quemaduras de 3er grado (piel carbonizada, blanca o negra): NO aplicar agua, cubrí inmediatamente con tela limpia seca",
                        "Pedir EMERGENCY médica si la quemadura compromete: rostro, manos, pies, genitales, articulaciones o áreas grandes",
                        "Para mitigar el dolor: administre ibuporofeno o paracetamol si dispone de ellos en su botiquín"
                    ),
                    warningNotes = "Las quemaduras por electricidad siempre requieren internación o control médico inmediato dado que provocan daño interno indetectable a simple vista."
                ),
                Guide(
                    id = "aux_6",
                    title = "Suero oral casero",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 5 min",
                    description = "Prevenís la deshidratación peligrosa por diarrea o vómitos excesivos.",
                    steps = listOf(
                        "Ingredientes clave: 1 litro de agua previamente purificada + 6 cucharaditas al ras de azúcar + 1 cucharadita de sal fina",
                        "Mezclá vigorosamente en un recipiente limpio hasta disolver absolutamente todo",
                        "El sabor final debe ser sutil: similar al de una lágrima (levemente dulce y salado)",
                        "Si sentís sabor muy salado, tiralo: un exceso de sal es peligroso y deshidrata más",
                        "Dosis en ADULTOS: tomar 200 a 400 ml luego de cada deposición líquida o vómito",
                        "Dosis en NIÑOS (1 a 8 años): tomar 100 a 150 ml luego de cada episodio",
                        "Dosis en BEBÉS: dar de 50 a 100 ml fraccionados con cucharitas cada 5 a 10 minutos para evitar que lo devuelva",
                        "Guardá el suero casero tapado por un plazo máximo de 24 horas. Descartá el sobrante"
                    ),
                    warningNotes = "El suero casero es una respuesta de emergencia. Si la deshidratación continúa por más de 12-24 horas, o nota hilos de sangre, busque ayuda médica."
                ),
                Guide(
                    id = "aux_7",
                    title = "Reconocer y actuar ante emergencias graves",
                    difficulty = "🟡 Moderado",
                    description = "Guía de identificación de emergencias médicas de alto riesgo en el hogar.",
                    steps = listOf(
                        "INFARTO: dolor opresivo persistente en el pecho que puede ir al brazo izquierdo, cuello o espalda, sudoración fría, náuseas, asfixia. Acción: sentarlo, aflojar ropa, dar aspirina 325mg masticada si es tolerada, llamar al 107.",
                        "ACV (Derrame): síntomas rápidos (FAST) - rostro torcido de un lado, brazo débil que cae, dificultad extrema para hablar. Acción: acostarlo de costado, no dar líquidos ni comida, no dar analgésicos, traslado urgente.",
                        "ANAFILAXIA: ronchas generalizadas, hinchazón de labios, lengua, garganta, dificultad severa para tragar. Acción: uso de autoinyector de epinefrina si posee, posición semi-sentada, urgencia.",
                        "CONVULSIONES: no sujetar a la persona, poner algo mullido bajo su cabeza, retirar objetos peligrosos del entorno. Lateralizar al terminar el movimiento.",
                        "HIPOTERMIA: temblores descontrolados, letargo, lentitud, labios morados. Acción: retirar vestimenta húmeda, abrigar progresivamente con sábanas/mantas el pecho y cabeza."
                    )
                )
            )
        ),
        Category(
            id = "comunicacion",
            name = "📻 Comunicación sin Internet",
            emoji = "📻",
            iconName = "Radio",
            guides = listOf(
                Guide(
                    id = "com_1",
                    illustrationAsset = "il_radio_galena",
                    title = "Radio AM/FM casera con componentes básicos",
                    difficulty = "🔴 Difícil",
                    time = "⏱ 2-4 horas",
                    description = "Construí una radio receptora simple para recibir señales de emergencia.",
                    steps = listOf(
                        "RADIO GALENA (la sintonizadora más simple): no demanda pilas para funcionar",
                        "Componentes mínimos: bobina de ferrita de radio desarmada (o bobinar 100 vueltas de cobre en tubo de cartón de 10cm), diodo de germanio (OA90 o 1N34A), capacitor sintonizador variable 365pF",
                        "Conectá la bobina al sintonizador en paralelo: forman el circuito resonante",
                        "Conectá el ánodo del diodo de germanio a la unión de la bobina y capacitor",
                        "Los auriculares deben ser de cristales de alta impedancia (2000 ohms), los comunes no se escucharán",
                        "Antena de cable: tendé un cable de cobre largo de 10 a 20 metros horizontalmente y lo más alto posible",
                        "Toda radio galena requiere conexión a TIERRA: soldá un cable a caños de cobre metálicos de agua o clavá una reja al suelo húmedo",
                        "Mové la perilla del capacitor para buscar emisoras AM locales con transmisión activa"
                    ),
                    warningNotes = "El diodo de silicio (1N4007) común NO funciona para galena porque posee mucha barrera de tensión. Es indispensable un diodo de germanio.",
                    tipNotes = "En la República Argentina, Radio Nacional AM 870 es la emisora oficial de catástrofes y avisos nacionales. Con este receptor autónomo podés captarla sin depender de baterías."
                ),
                Guide(
                    id = "com_2",
                    title = "Usar el celular como radio FM",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 2 min",
                    description = "Sintonizá noticias locales sin cobertura telefónica usando receptores embebidos.",
                    steps = listOf(
                        "Los auriculares con cable físico de 3.5mm actúan como antena y son INDISPENSABLES para habilitar el sintonizador analógico",
                        "Enchufá el auricular firmemente en el jack del celular antes de lanzar el software",
                        "Busca la aplicación denominada 'Radio FM' o 'FM Radio' preinstalada por el fabricante",
                        "En caso de que no figure, descargá de fuentes confiables (como 'NextRadio') antes de la caída de redes",
                        "Dial de referencia en Argentina:\n- Radio Nacional AM 870 / FM 98.7 (emisiones nacionales)\n- Redes de radiodifusión locales",
                        "Utilizá la función de altavoz de la aplicación si precisás que tu grupo familiar escuche la transmisión",
                        "La FM rinde excelente nitidez pero limitada en área (cobertura típica 50km). AM viaja mucho más"
                    ),
                    tipNotes = "Podés usar la radio analógica en modo avión, ahorrando hasta un 80% de batería comparado con streaming de radio por datos móviles."
                ),
                Guide(
                    id = "com_3",
                    title = "Señales visuales y Código Morse",
                    difficulty = "🟡 Moderado",
                    description = "Aprendé los códigos de auxilio visual para alertar a brigadas de rescate.",
                    steps = listOf(
                        "SOS en morse con luz/linterna: tres destellos muy cortos (puntos), tres prolongados (rayas), tres cortos",
                        "Símbolos de suelo universales:\n- SOS o X: Ayuda requerida\n- Flechas de dirección: Me moví hacia esa ruta\n- F: Preciso agua o comida\n- I: Preciso primeros auxilios médicos",
                        "Espejo de heliógrafo: reflejá el haz del sol orientando hacia la aeronave avistada. Útil a distancias de 10km",
                        "Señales de fuego y humo:\n- Día: madera húmeda/hojas verdes produce humo blanco nítido\n- Noche: fogata abierta en lugares planos o cúspides de cerros",
                        "El conteo de TRES: efectúe tres ruidos o señales repetidas a intervalos regulares para señalar desastre."
                    )
                ),
                Guide(
                    id = "com_4",
                    title = "Red de comunicación entre vecinos",
                    difficulty = "🟢 Fácil",
                    description = "Organizá la comunicación familiar y de cercanía ante apagones de redes.",
                    steps = listOf(
                        "Acordar previamente un punto geográfico seguro del barrio (ej. plazas, centros vecinales)",
                        "Código de alerta en aberturas: ventana iluminación prendida = bien, apagada prolongada = asistencia, parpadeo = advertencia",
                        "Fijar horarios estrictos de asamblea o chequeo preventivo (ej. 09:00 y 19:00 horas)",
                        "Estaciones portátiles de radio UHF/VHF (Handies): excelente opción barata autogestiva libre de antenas públicas",
                        "App Meshtastic: permite mensajes encriptados grupales sin antenas aliando celulares vía BLE a placas LoRa baratas",
                        "Golpes estructurales acústicos: 2 golpes en cañería/pared = todo en orden; 3 golpes fuertes seguidos = alerta"
                    )
                )
            )
        ),
        Category(
            id = "proteccion",
            name = "🛡️ Protección y Aislamiento",
            emoji = "🛡️",
            iconName = "Shield",
            guides = listOf(
                Guide(
                    id = "prot_1",
                    illustrationAsset = "il_mascarilla",
                    title = "Mascarilla casera - 3 tipos",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 10-15 min",
                    description = "Confeccioná mascarillas básicas contra inhalación de polvos gruesos o cenizas.",
                    steps = listOf(
                        "TIPO 1 - ALGODÓN (Barrera física simple):\n- Cortá dos paños de sábana o remera de 25x15 cm\n- Doblá en tres pliegues para mayor ajuste y cosé los elásticos laterales",
                        "TIPO 2 - CON BOLSILLO DE FILTROS:\n- Dejá un hueco en los bordes interiores cosidos para añadir materiales filtrantes\n- Canales aptos: papel absorbente doblado en dos, papel de cafetera o paño hidrófugo de bolsa ecológica",
                        "TIPO 3 - EMERGENCIA DE SEGUNDOS:\n- Deslizá una remera de algodón sobre tu cabeza, utilizando el cuello sobre tu tabique para cubrir la boca",
                        "Inserta un retazo de alambre plastificado en el puente superior para sellar",
                        "Lavá la tela con cloro/lavandina y agua caliente luego de cada incursión exterior"
                    ),
                    warningNotes = "Estas protecciones caseras NO sirven contra monóxido de carbono, gases inflamables, pérdidas químicas de cloro ni vapores tóxicos."
                ),
                Guide(
                    id = "prot_2",
                    title = "Sellar una habitación de emergencia",
                    difficulty = "🟡 Moderado",
                    time = "⏱ 20-30 min",
                    description = "Aislá tu ambiente contra fugas, emanaciones tóxicas o nubes nocivas exteriores.",
                    steps = listOf(
                        "Selecciona la habitación más central e interna de tu casa (menos ventanas expuestas)",
                        "Sella la hendija de la base de la puerta de ingreso enrollando una toalla o trapo humedecido con agua",
                        "Cinta de embalaje: tapona todo el perímetro de la abertura cubriendo las ranuras residuales",
                        "Ventanas: clausura con cinta, si el panel posee fisuras pega películas de cinta cruzadas",
                        "Conductores de aire: cubre con bolsas plásticas de consorcio tensadas y selladas con cinta",
                        "Cortá e inhabilita las rejillas del baño y chimeneas para anular la succión del aire exterior",
                        "Cubre con tiras adhesivas los tomacorrientes e interruptores eléctricos (comunican canales de aire internos)"
                    ),
                    warningNotes = "Una habitación hermética tiene cupo de oxígeno limitado para humanos. Ventila moderadamente tras varias horas si nota dolor de cabeza.",
                    tipNotes = "Usa recipientes plásticos grandes llenos de agua en la sala antes de sellarla para contar con higiene rápida."
                ),
                Guide(
                    id = "prot_3",
                    title = "Descontaminación al entrar del exterior",
                    difficulty = "🟡 Moderado",
                    time = "⏱ 10-15 min",
                    description = "Evitá meter sustancias peligrosas en el foco residencial.",
                    steps = listOf(
                        "Fija una zona transitoria intermedia en la puerta o recibidor",
                        "Sacate la ropa exterior de arriba abajo. Métela enseguida en una bolsa plástica sin sacudirla en el aire",
                        "Quitás las suelas o calzados y colocalos en un balde con solución de lavandina diluida",
                        "Limpia profusamente manos, uñas y cara utilizando agua limpia y jabón neutro",
                        "Dúchate de forma completa prestando atención a tus cabellos",
                        "Contaminación con químicos: lávate usando agua FRÍA, el agua caliente abre poros cutáneos agilizando el ingreso",
                        "Lavá la ropa por separado con programas térmicos extensos"
                    ),
                    warningNotes = "No dejes ingresar calzados exteriores sucios a las zonas de descanso familiar."
                ),
                Guide(
                    id = "prot_4",
                    title = "Velas caseras - 3 métodos",
                    difficulty = "🟢 Fácil",
                    description = "Fabricación de velas y soportes lumínicos de duración ilimitada.",
                    steps = listOf(
                        "MÉTODO 1 - ACEITE COMÚN: Verter aceite de freír o cocinar en un frasco chico. Colgar una mecha de algodón frotada en cera y prender.",
                        "MÉTODO 2 - PARAFINA DE SALVATAJE:\n- Junta pedazos de velas derretidas que encuentres\n- Derrítelas en una cacerola pequeña sumergida en agua caliente \n- Fabrica pabilo con piolín grueso, colócalo tenso al medio del molde moldeado con cartón, vuelca el líquido caliente y deja endurecer.",
                        "MÉTODO 3 - SEBO ANIMAL: Fundí grasa vacuna o porcina en sartén. Filtra para retirar restos de carbón y vacía en un pocillo con su mecha."
                    ),
                    warningNotes = "Ubica siempre los mecheros y velas en soportes metálicos o de losa pesada para evitar caídas incidentales."
                )
            )
        ),
        Category(
            id = "alimentos",
            name = "🥫 Alimentos y Conservación",
            emoji = "🥫",
            iconName = "Kitchen",
            guides = listOf(
                Guide(
                    id = "ali_1",
                    title = "Alimentos esenciales para stockear",
                    difficulty = "🟢 Fácil",
                    description = "Ración básica calculada para abastecer un adulto por el plazo de 30 días.",
                    steps = listOf(
                        "AGUA POTABLE: El pilar número uno. Estima 2 litros diarios para tomar (arman 60 litros mensuales por cabeza)",
                        "ARROZ BLANCO: Carga 5 kg por persona. Es el hidrato más durable (5 a 10 años en empaque cerrado)",
                        "FIDEOS SECOS: 3 kg por mes. Gran rendimiento, cocción rápida",
                        "AVENA EN HOJUELAS: 2 kg. Magnífica para nutrición fría sin prender fogata",
                        "HARINA DE TRIGO: 3 kg. Muy versátil para amasar panes rústicos",
                        "LENTEJAS Y POROTOS: 3 kg secas. Excelente proteína vegetal de enorme plazo de almacenamiento",
                        "ATÚN O SARDINAS EN LATA: 12 unidades por persona. Energía lipídica directa y confiable",
                        "LECHE EN POLVO: 1 kg. Aporte decalcio elemental",
                        "ACEITE DE COCINAR: 2 litros (energía densa, rinde además para luces)",
                        "HIGIENE Y SABOR: Sal fina (1 kg), Azúcar (2 kg), Miel de abejas (500g, no vence nunca)",
                        "COMPLEMENTO: Vinagre de alcohol para aderezos o conservación rústica",
                        "SUPLEMENTOS: un bote de polivitamínicos para compensar deficiencias"
                    ),
                    tipNotes = "Cada semana en el súper, comprá dos latas de atún más de lo habitual para rotar recursos fácilmente sin gasto grande inmediato."
                ),
                Guide(
                    id = "ali_2",
                    title = "Conservar alimentos sin heladera",
                    difficulty = "🟡 Moderado",
                    description = "Procedimientos para conservar carnes, hortalizas y quesos libres de hongos.",
                    steps = listOf(
                        "Busca el pozo, subsuelo o placard más oscuro e interior de la casa",
                        "CARNES (CHARQUI DE SAL):\n- Cortá carne en rebanadas finas quitando toda la grasa lateral\n- Sumergí la carne en sal gruesa cubriéndola 12 horas para deshidratar\n- Colgá a secar al sol sutil durante 2 a 3 días hasta quedar dura",
                        "CONSERVACIÓN EN ESCABECHE: Cocinar trozos y guardarlos cubiertos en vinagre blanco puro con especias",
                        "FÉCULAS EN ARENA FRESCA: Zanahorias, remolachas, rabanitos y tubérculos duran semanas sumergidos en cajitas de arena lavada seca",
                        "QUESO DE CORTESA DURA: Envuelto en un paño de hilo humedecido con agua de sal pesada frena el moho exterior",
                        "Comidas elaboradas sobrantes: consúmalas dentro del lapso de 18-24 horas si no posee refrigeración activa"
                    ),
                    warningNotes = "Si un fiambre, conserva o carne presenta olor a rancio, textura babosa o tapa de lata hinchada, descártelo en el acto. Las toxinas son mortales."
                ),
                Guide(
                    id = "ali_3",
                    title = "Cocina de emergencia sin gas ni luz",
                    difficulty = "🟡 Moderado",
                    description = "Alternativas para hervir y guisar comida sin conexiones domésticas.",
                    steps = listOf(
                        "COCINA DE REFRACCIÓN SOLAR:\n- Fabrica un cono o embudo de cartón y fórralo por dentro con papel aluminio\n- En el centro posiciona una cacerola pintada en esmalte oscuro mate para captar calor\n- Bajo incidencia solar fuerte calienta agua y guisa vegetales en 1 hora",
                        "MECHERO ECONÓMICO DE ALCOHOL: Utiliza una lata de refresco pequeña recortando el fondo. Vuelca un chorro de alcohol etílico de 96° y prende con chispa",
                        "REJILLAS DE CALEFACCIÓN POR VELAS: Coloca 4-6 velas en un plato plano, monta rejilla de alambre encima para entibiar sopas de forma lenta",
                        "FUEGOS TRIPOLARES EXTERIORES: Colocar tres ladrillos en cuña, alimentar con ramitas delgadas para fuego rápido y de bajo humo",
                        "Truco térmico: Remoja lentejas y legumbres 8-12 horas antes. Esto rebaja el tiempo de fuego de 60 a solo 15 minutos."
                    ),
                    warningNotes = "No operés mecheros, carbones ni maderas ardiendo adentro de domicilios sin ventilación. El monóxido de carbono es inodoro y asfixia."
                ),
                Guide(
                    id = "ali_4",
                    title = "Hacer fuego sin fósforos ni encendedor",
                    difficulty = "🔴 Difícil",
                    time = "⏱ Variable",
                    description = "Aprende los cuatro métodos físicos para provocar llamas sin insumos clásicos.",
                    steps = listOf(
                        "PREPARAR LA YESCA: El paso crucial. Use copos de algodón, hilachas de soga seca, virutas finas de madera o pelusas de ropa de bolsillo",
                        "MÉTODO 1 - CORTOCIRUITO POR BATERÍA:\n- Roza los bornes positivo y negativo de una batería de 9 V con virulana fina de acero (lana de pulir metálica)\n- Se encenderá de inmediato. Colócala raudamente debajo de tu yesca",
                        "MÉTODO 2 - ÓPTICA DE LUPAS O LENTES:\n- Concentre los rayos del sol orientando sobre la yesca utilizando el fondo de una botella rota, lentes de lectura potentes o lupas de mano\n- Mantenga el pulso rígido hasta asomar columna de humo sutil y sople con suavidad",
                        "MÉTODO 3 - PEDERNAL Y REPUJADO:\n- Golpea piedras con cuarzo o sílex contra el canto de un cuchillo de carbono a 45 grados\n- Deja llover las pequeñas chispas directo encima del material de yesca",
                        "MÉTODO 4 - ARCO Y FRICCIÓN: Girar frotando un palo de madera dura en un calado lateral en madera blanda seca. Cansador"
                    ),
                    tipNotes = "Añade una batería de 9 V y un trozo pequeño de virulana metálica en tu mochila de escape. Es un método 100% infalible incluso con viento.",
                    householdSources = listOf(
                        "🔋 Batería de 9V: control remoto de TV, detector de humo, juguetes",
                        "🧹 Virulana de acero: cocina o alacena (comprá la sin jabón, o enjuagá bien la común)",
                        "🔍 Lupa: anteojos de leer tienen lente convergente que funciona igual que lupa",
                        "🪣 Yesca: pelusa del filtro de la secadora, algodón del botiquín, papel de diario seco triturado",
                        "🪞 Espejo o CD viejo: refleja y concentra la luz solar sobre la yesca"
                    )
                ),
                Guide(
                    id = "ali_5",
                    title = "Cultivo de emergencia - germinados",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 3-7 días",
                    description = "Generá brotes verdes crocantes llenos de vitaminas en tu mesa sin tierra ni abonos.",
                    steps = listOf(
                        "Semillas aptas de rápido brote: lentejas crudas comunes, soja verde/mung, trigo, alfalfa, garbanzos",
                        "Cálculo: Dos cucharadas soperas de granos rinden casi una taza repleta de brotes comestibles",
                        "DÍA 1 - INICIO: Lava las semillas seleccionadas, colócalas en un frasco de vidrio cubiertas de agua por 8 a 12 horas",
                        "DÍA 1 - DRENAJE: Vierte el exceso de agua. Colocá una gasa en la boca sujeta con goma elástica e inclina boca abajo (45 grados) sobre un plato",
                        "DÍAS 2 a 5 - ENJUAGUE: Dos veces por jornada (matina y noche) llena el frasco con agua, agita sutil, drena y vuelve a inclinar",
                        "Verás salir raicillas blancas en 48 horas. Al quinto día sumarán hojitas verdes listas para comer",
                        "Consúmelos frescos. Tienen alto valor de vitamina C y aminoácidos"
                    ),
                    warningNotes = "Si la germinación despide aroma mohoso, viscosidad o manchas negras, arrójelo. Sucede por exceso de calor o drenaje indebido."
                )
            )
        ),
        Category(
            id = "navegacion",
            name = "🗺️ Navegación y Refugio",
            emoji = "🗺️",
            iconName = "Navigation",
            guides = listOf(
                Guide(
                    id = "nav_1",
                    illustrationAsset = "il_orientacion",
                    title = "Orientarse sin GPS ni brújula",
                    difficulty = "🟡 Moderado",
                    description = "Trucos de astronomía y naturaleza para orientar tus rumbos cardinales.",
                    steps = listOf(
                        "EL SOL EN EL HEMISFERIO SUR (Argentina):\n- El sol sale aproximadamente por el Este y se oculta en el Oeste\n- Al mediodía solar, si miras al sol, estarás contemplando directamente el NORTE geográfico",
                        "CRUZ DEL SUR (De noche):\n- Localiza la constelación Crux\n- Prolonga mentalmente la línea del eje mayor 4.5 veces hacia abajo\n- Tracen una plomada al horizonte: ese punto representa exactamente el SUR",
                        "MÉTODO DE LA SOMBRA Y EL PALO:\n- Entierra un palo recto en el piso plano y marca con una piedra la punta de la sombra\n- Deja pasar 20 minutos y marca la nueva punta de la sombra\n- Una línea que conecte la primera marca con la segunda va de OESTE a ESTE",
                        "SEÑALES VEGETALES SUR/NORTE:\n- En Argentina, el musgo más verde crece en las cortezas orientadas al SUR (menos luz solar, humedad conservada)\n- Las laderas del norte en montañas reciben mucha más resolana"
                    )
                ),
                Guide(
                    id = "nav_2",
                    title = "Refugio de emergencia interior",
                    difficulty = "🟢 Fácil",
                    time = "⏱ 20-30 min",
                    description = "Mejorá la habitabilidad térmica dentro de tu casa ante cortes extensos de calefacción.",
                    steps = listOf(
                        "Aísla el piso de tus pies: cubre baldosas con capas de cartón corrugado, diarios o mantas viejas",
                        "Ventanales del hogar: cubre la cara interna de vidrios fijando plásticos de burbujas o frazadas de invierno",
                        "Microclima en cama: arma una estructura de carpa/fuerte sobre tu sommier tirando colchas pesadas. Retiene el aire tibio del propio cuerpo",
                        "Concentración de calor: junten a toda la familia a dormir en un mismo dormitorio central. El calor metabólico suma varios grados",
                        "Tape hendiduras inferiores de las puertas usando bolsas de telas rellenas de papel compacto",
                        "Mantén cerradas las puertas de habitaciones sin uso para no disipar la ganancia térmica"
                    )
                ),
                Guide(
                    id = "nav_3",
                    title = "Señales de evacuación y puntos de encuentro",
                    difficulty = "🟢 Fácil",
                    description = "Organizá previamente el escape controlado familiar en catástrofes.",
                    steps = listOf(
                        "Señalar punto básico familiar 1: una esquina/plaza del barrio a menos de 4 cuadras. Salida rápida",
                        "Señalar punto básico familiar 2: domicilio de amigos o parientes a salvo en otra localidad",
                        "Dejar marcas explícitas: ante evacuación forzosa, trace una tiza/nota en la puerta indicando hora, rumbos y firmas",
                        "Establezca de antemano rutas viales seguras alternativas. Las avenidas troncales suelen colapsar por vehículos",
                        "Mochila de emergencia ágil de 72 horas por persona: botiquín, agua, alimentos, radio, linterna, libreta de contactos clave escrita, copias de documentos (DNI) metidas en bolsas plásticas con cierre"
                    )
                )
            )
        )
    )

    // Helper function to search all guides (or sections)
    fun searchGuides(query: String): List<Pair<Category, Guide>> {
        if (query.isBlank()) return emptyList()
        val lowerQuery = query.lowercase().trim()
        val results = mutableListOf<Pair<Category, Guide>>()
        for (category in categories) {
            for (guide in category.guides) {
                if (guide.title.lowercase().contains(lowerQuery) ||
                    guide.description.lowercase().contains(lowerQuery) ||
                    guide.steps.any { it.lowercase().contains(lowerQuery) } ||
                    (guide.warningNotes?.lowercase()?.contains(lowerQuery) == true) ||
                    (guide.tipNotes?.lowercase()?.contains(lowerQuery) == true)) {
                    results.add(category to guide)
                }
            }
        }
        return results
    }
}
