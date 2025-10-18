// Simple date formatting helper
function formatDateTime(dateTimeStr) {
    const date = new Date(dateTimeStr);
    return date.toLocaleString();
}

// Confirm delete actions
function confirmDelete(action) {
    return confirm(`Are you sure you want to delete this ${action}?`);
}
<div class="cinema-container">
    <div class="header-time">
        <span class="time-slot">08:30 PM</span>
        <span class="time-slot">09:15 PM</span>
    </div>

    <div class="seating-chart">

        <div class="section-label">DIR'S LOUNGE</div>
        <div class="seat-row lounge-row" id="lounge">
            <div class="seat available" data-row="DL" data-num="12">DL12</div>
            <div class="seat available" data-row="DL" data-num="11">DL11</div>
            <div class="seat available" data-row="DL" data-num="10">DL10</div>
            <div class="seat available" data-row="DL" data-num="9">DL9</div>
            <div class="seat available" data-row="DL" data-num="8">DL8</div>
            <div class="seat available" data-row="DL" data-num="7">DL7</div>
            <div class="seat available" data-row="DL" data-num="6">DL6</div>
            <div class="seat available" data-row="DL" data-num="5">DL5</div>

            <div class="spacer large-spacer"></div>

            <div class="seat available" data-row="DL" data-num="4">DL4</div>
            <div class="seat available" data-row="DL" data-num="3">DL3</div>
            <div class="seat available" data-row="DL" data-num="2">DL2</div>
            <div class="seat available" data-row="DL" data-num="1">DL1</div>
        </div>

        <div class="section-label">STANDARD</div>
        <div class="standard-grid">
            <div class="seat-row" data-row="J">
                <div class="seat available">J18</div><div class="seat available">J17</div><div class="seat available">J16</div><div class="seat available">J15</div><div class="seat available">J14</div><div class="seat available">J13</div><div class="seat available">J12</div><div class="seat available">J11</div>
                <div class="seat selected">J10</div><div class="seat selected blue">J9</div><div class="seat booked">J8</div><div class="seat booked">J7</div>
                <div class="spacer small-spacer"></div>
                <div class="seat booked">J6</div><div class="seat booked">J5</div><div class="seat booked">J4</div><div class="seat booked">J3</div><div class="seat available">J2</div><div class="seat available">J1</div>
            </div>

            <div class="seat-row" data-row="I">
                <div class="seat available">I18</div><div class="seat available">I17</div><div class="seat available">I16</div><div class="seat available">I15</div><div class="seat available">I14</div>
                <div class="seat selected blue">I13</div><div class="seat selected blue">I12</div><div class="seat selected">I11</div><div class="seat selected">I10</div><div class="seat selected blue">I9</div><div class="seat booked">I8</div><div class="seat available">I7</div>
                <div class="spacer small-spacer"></div>
                <div class="seat booked">I6</div><div class="seat booked">I5</div><div class="seat booked">I4</div><div class="seat booked">I3</div><div class="seat available">I2</div><div class="seat available">I1</div>
            </div>

            <div class="seat-row" data-row="H">
                <div class="seat available">H18</div><div class="seat available">H17</div><div class="seat available">H16</div><div class="seat available">H15</div><div class="seat available">H14</div><div class="seat available">H13</div><div class="seat available">H12</div>
                <div class="seat selected">H11</div><div class="seat selected">H10</div><div class="seat booked">H9</div><div class="seat booked">H8</div><div class="seat booked">H7</div>
                <div class="spacer small-spacer"></div>
                <div class="seat available">H6</div><div class="seat available">H5</div><div class="seat available">H4</div><div class="seat available">H3</div><div class="seat available">H2</div><div class="seat available">H1</div>
            </div>

            <div class="seat-row" data-row="G">
                <div class="seat available">G16</div><div class="seat available">G15</div><div class="seat available">G14</div><div class="seat available">G13</div><div class="seat available">G12</div><div class="seat available">G11</div><div class="seat available">G10</div><div class="seat available">G9</div><div class="seat available">G8</div><div class="seat available">G7</div><div class="seat available">G6</div><div class="seat booked">G5</div>
                <div class="spacer small-spacer"></div>
                <div class="seat available">G4</div><div class="seat available">G3</div><div class="seat available">G2</div><div class="seat available">G1</div>
            </div>

            <div class="seat-row" data-row="F">
                <div class="seat available">F16</div><div class="seat available">F15</div><div class="seat available">F14</div><div class="seat available">F13</div>
                <div class="seat selected">F12</div><div class="seat selected">F11</div><div class="seat selected">F10</div><div class="seat selected">F9</div><div class="seat booked">F8</div><div class="seat booked">F7</div><div class="seat booked">F6</div><div class="seat booked">F5</div>
                <div class="spacer small-spacer"></div>
                <div class="seat available">F4</div><div class="seat available">F3</div><div class="seat available">F2</div><div class="seat available">F1</div>
            </div>

            <div class="seat-row" data-row="E">
                <div class="seat available">E16</div><div class="seat available">E15</div><div class="seat available">E14</div><div class="seat available">E13</div><div class="seat available">E12</div><div class="seat available">E11</div><div class="seat available">E10</div><div class="seat available">E9</div><div class="seat available">E8</div><div class="seat available">E7</div>
                <div class="seat booked">E6</div><div class="seat booked">E5</div>
                <div class="spacer small-spacer"></div>
                <div class="seat booked">E4</div><div class="seat booked">E3</div><div class="seat available">E2</div><div class="seat available">E1</div>
            </div>

            <div class="seat-row" data-row="D">
                <div class="seat available">D16</div><div class="seat available">D15</div><div class="seat available">D14</div><div class="seat available">D13</div><div class="seat available">D12</div><div class="seat available">D11</div><div class="seat available">D10</div><div class="seat available">D9</div><div class="seat available">D8</div><div class="seat available">D7</div><div class="seat available">D6</div><div class="seat available">D5</div>
                <div class="spacer small-spacer"></div>
                <div class="seat available">D4</div><div class="seat available">D3</div><div class="seat available">D2</div><div class="seat available">D1</div>
            </div>

            <div class="seat-row" data-row="C">
                <div class="seat available">C16</div><div class="seat selected">C15</div><div class="seat available">C14</div><div class="seat available">C13</div><div class="seat selected blue">C12</div><div class="seat selected">C11</div><div class="seat available">C10</div><div class="seat available">C9</div><div class="seat available">C8</div><div class="seat available">C7</div><div class="seat available">C6</div><div class="seat available">C5</div>
                <div class="spacer small-spacer"></div>
                <div class="seat available">C4</div><div class="seat available">C3</div><div class="seat available">C2</div><div class="seat available">C1</div>
            </div>

            <div class="seat-row" data-row="B">
                <div class="seat available">B16</div><div class="seat available">B15</div><div class="seat available">B14</div><div class="seat available">B13</div><div class="seat available">B12</div><div class="seat available">B11</div><div class="seat available">B10</div><div class="seat available">B9</div><div class="seat available">B8</div><div class="seat available">B7</div><div class="seat available">B6</div><div class="seat available">B5</div>
                <div class="spacer small-spacer"></div>
                <div class="seat available">B4</div><div class="seat available">B3</div><div class="seat available">B2</div><div class="seat available">B1</div>
            </div>

            <div class="seat-row" data-row="A">
                <div class="seat available">A16</div><div class="seat available">A15</div><div class="seat available">A14</div><div class="seat available">A13</div><div class="seat available">A12</div><div class="seat available">A11</div><div class="seat available">A10</div><div class="seat available">A9</div><div class="seat available">A8</div><div class="seat available">A7</div><div class="seat available">A6</div><div class="seat available">A5</div>
                <div class="spacer small-spacer"></div>
                <div class="seat available">A4</div><div class="seat available">A3</div><div class="seat available">A2</div><div class="seat available">A1</div>
            </div>

        </div>

        <div class="screen-label">SCREEN</div>
    </div>
</div>

<script th:src="@{/js/script.js}"></script>